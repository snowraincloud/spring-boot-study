package priv.wjh.security;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MybatisMapperCheck {

    @Test
    public void test() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        String path = "classpath*:com/wanshun/usercenter/infrastructure/mysql/mapper/**/*.xml";
        ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
        Resource[] mapperReourcesmapperReources = resourceLoader.getResources(path);
        Configuration configuration = new Configuration();
        configuration.setCacheEnabled(false);
        configuration.setLocalCacheScope(LocalCacheScope.STATEMENT);
        configuration.setJdbcTypeForNull(JdbcType.NULL);

        for (Resource mapperLocation : mapperReourcesmapperReources) {
            if (mapperLocation == null) {
                continue;
            }
            try {
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(mapperLocation.getInputStream(),
                                                                         configuration, mapperLocation.toString(),
                                                                         configuration.getSqlFragments());
                xmlMapperBuilder.parse();
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse mapping resource: '" + mapperLocation + "'", e);
            } finally {
                ErrorContext.instance()
                        .reset();
            }
        }

        Set<String> set = new HashSet<>();
        for (Object mappedStatement : configuration.getMappedStatements()) {
            if (mappedStatement instanceof MappedStatement){
                set.add(((MappedStatement)mappedStatement).getId());
            }
        }

        List<String> idList = new ArrayList<>(set);
//        idList.sort(new ASCIICaseInsensitiveComparator());
        for (String id : idList) {
            try {
                check(configuration, id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("---------");
    }

    private static void check(Configuration configuration, String id) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        int i = id.lastIndexOf(".");
        String classId = id.substring(0, i);
        Method methodByName = getMethod(classId, id.substring(i +1));
        if (methodByName == null){
            return;
        }
        Object[] args = new Object[methodByName.getParameterTypes().length];
        int j = 0;
        for (Parameter parameter : methodByName.getParameters()) {
            Type parameterizedType = parameter.getParameterizedType();
            if (parameterizedType instanceof ParameterizedType){
                String typeName = ((ParameterizedType) parameterizedType).getRawType()
                        .getTypeName();
                switch (typeName){
                    case "java.util.List": {
                        ArrayList<Object> objects = new ArrayList<>();
                        Object object = createObject(((ParameterizedType) parameterizedType).getActualTypeArguments()[0].getTypeName());
                        objects.add(object);
                        args[j++] = objects;
                        break;
                    }
                    case "java.util.Set": {
                        HashSet<Object> objects = new HashSet<>();
                        Object object = createObject(((ParameterizedType) parameterizedType).getActualTypeArguments()[0].getTypeName());
                        objects.add(object);
                        args[j++] = objects;
                        break;
                    }
                    default:{
                        throw new RuntimeException(typeName);
                    }
                }
            }else {
                args[j++] = createObject(parameterizedType.getTypeName());
            }
        }
        ParamNameResolver paramNameResolver = new ParamNameResolver(configuration, methodByName);
        Object namedParams = paramNameResolver.getNamedParams(args);

        MappedStatement mappedStatement = configuration.getMappedStatement(id);

        BoundSql boundSql = mappedStatement.getBoundSql(namedParams);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
        for (int k = 0; k < parameterMappings.size(); k++) {
            ParameterMapping parameterMapping = parameterMappings.get(k);
            if (parameterMapping.getMode() != ParameterMode.OUT) {
                Object value;
                String propertyName = parameterMapping.getProperty();
                if (boundSql.hasAdditionalParameter(propertyName)) {
                    value = boundSql.getAdditionalParameter(propertyName);
                } else if (namedParams == null) {
                    value = null;
                } else if (typeHandlerRegistry.hasTypeHandler(namedParams.getClass())) {
                    value = namedParams;
                } else {
                    try {
                        MetaObject metaObject = configuration.newMetaObject(namedParams);
                        value = metaObject.getValue(propertyName);
                    }catch (Exception e){
                        e.printStackTrace();
                        value = "Error Parameter "+ namedParams +" not found";
                    }
                }
                System.out.println(id + "#" + propertyName + ":" + value);
            }
        }
    }

    private static Method getMethod(String classId, String methodName) throws ClassNotFoundException {
        for (Method method : Class.forName(classId)
                .getMethods()) {
            if (method.getName().equals(methodName)){
                return method;
            }
        }
        return null;
    }


    private static Object createObject(String typeName) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        switch (typeName){
            case "int":
            case "java.lang.Integer":{
                return 0;
            }
            case "long":
            case "java.lang.Long":{
                return 0L;
            }
            case "boolean":
            case "java.lang.Boolean":{
                return true;
            }
            default:
                return (Class.forName(typeName)).newInstance();
        }
    }
}
