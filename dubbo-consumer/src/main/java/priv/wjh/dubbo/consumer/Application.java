package priv.wjh.dubbo.consumer;


import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import priv.wjh.dubbo.api.GreetingsService;

public class Application {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "114.115.213.11");

    public static void main(String[] args) {
        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setApplication(new ApplicationConfig("first-dubbo-consumer"));
        reference.setRegistry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"));
        reference.setInterface(GreetingsService.class);
        GreetingsService service = reference.get();
        String message = service.sayHi("dubbo-jjjjjjjjjjj");
        System.out.println(message);
    }
}