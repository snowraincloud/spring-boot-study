package priv.wjh.dubbo.consumer;


import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.rpc.service.GenericService;
import priv.wjh.dubbo.api.GreetingsService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class DubboConsumerApplication {
    private static String zookeeperHost = System.getProperty("zookeeper.address", "114.115.213.11");

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ReferenceConfig<GreetingsService> reference = new ReferenceConfig<>();
        reference.setInterface(GreetingsService.class);
        reference.setTimeout(100000);
        // 开启后不支持future异步
//        reference.setGeneric("true");


        DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(new ApplicationConfig("dubbo-demo-api-consumer"))
                .registry(new RegistryConfig("zookeeper://" + zookeeperHost + ":2181"))
                .protocol(new ProtocolConfig(CommonConstants.DUBBO, -1))
                .reference(reference)
                .start();

        GreetingsService demoService = bootstrap.getCache().get(reference);
//        String message = demoService.sayHi("dubbo-jjjjjjjjjjj");
//        System.out.println(message);
        CompletableFuture<String> async = demoService.sayHi("async", true);
        System.out.println("1111111");
        System.out.println(async.get());

        // generic invoke
//        GenericService genericService = (GenericService) demoService;
//        Object genericInvokeResult = genericService.$invoke("sayHello", new String[]{String.class.getName()},
//                                                            new Object[]{"dubbo generic invoke"});
//        System.out.println(genericInvokeResult);
    }
}
