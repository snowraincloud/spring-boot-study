package priv.wjh.study.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;


@ComponentScan("priv.wjh.study.http")
@SpringBootApplication
@Slf4j
public class HttpInterfacesApplication {
    public static void main(String[] args) {
        SpringApplication.run(HttpInterfacesApplication.class, args);
    }

    @Bean
    public TodoClient todoClient() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .build();
        return httpServiceProxyFactory.createClient(TodoClient.class);
    }

    @Bean
    CommandLineRunner clr(TodoClient todoClient){
        return args -> {
            log.info(todoClient.getTodo(1).toString());
            log.info(todoClient.save(new Todo(null, 1L, "看书", false)).toString());
            for(Todo todo : todoClient.getTodos()){
                log.info(todo.toString());
            }
        };
    }
}
