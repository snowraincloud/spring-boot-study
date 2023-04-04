package priv.wjh.dubbo.api;

import java.util.concurrent.CompletableFuture;


public interface GreetingsService {
    String sayHi(String name);

    default CompletableFuture<String> sayHi(String name, boolean async){
        System.out.println("async: " + async);
        return CompletableFuture.completedFuture(sayHi(name));
    }
}
