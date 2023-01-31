package priv.wjh.test.factory.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class MyFactoryBean implements FactoryBean<MyBean>, InitializingBean {

    @Override
    public MyBean getObject() throws Exception {
        return new MyBean();
    }

    @Override
    public Class<?> getObjectType() {
        return MyBean.class;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("my factory bean after properties set");
    }
}
