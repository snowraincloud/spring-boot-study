package priv.wjh.study.spring.life.ext;

import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yangyu
 * @Date: 2022/12/3 16:38
 */
@Setter
@Configuration
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {


    @Value("#{me.test}")
    private String test;

    private static Map<String, String> urlMap = new HashMap<>();

    static {
        //urlMap.put("service-market", "127.0.0.1:18011");
        urlMap.put("service-order", "127.0.0.1:18012");
        urlMap.put("service-spu", "127.0.0.1:18016");
        urlMap.put("service-base", "127.0.0.1:18017");
        //urlMap.put("service-user", "127.0.0.1:18018");
        urlMap.put("service-combination", "127.0.0.1:18019");
        urlMap.put("service-wholesale", "127.0.0.1:18020");
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        for (String beanDefinitionName : beanDefinitionRegistry.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanDefinitionRegistry.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            if ("org.springframework.cloud.openfeign.FeignClientFactoryBean".equals(beanClassName)) {
                MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
                PropertyValue propertyValue = propertyValues.getPropertyValue("name");
                String name = (String) propertyValue.getValue();
                if (!urlMap.containsKey(name)) {
                    continue;
                }
                String url = urlMap.get(name);
                propertyValues.add("url", url);
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }
}
