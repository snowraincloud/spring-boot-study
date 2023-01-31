package priv.wjh.dubbo.provider;


import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.ServiceConfig;
import org.apache.dubbo.rpc.model.ApplicationModel;
import priv.wjh.dubbo.api.GreetingsService;

import java.util.concurrent.CountDownLatch;

public class Application {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "114.115.213.11");

    public static void main(String[] args) throws Exception {
        ServiceConfig<GreetingsService> service = new ServiceConfig<>();
        service.setScopeModel(ApplicationModel.defaultModel());
        service.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        service.setInterface(GreetingsService.class);
        service.setRef(new GreetingsServiceImpl());
        service.export();
        System.out.println("dubbo service started");
        new CountDownLatch(1).await();
    }
}