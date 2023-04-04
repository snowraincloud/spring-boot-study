package priv.wjh.study.http;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import java.util.List;

@HttpExchange("/todos")
public interface TodoClient {

    @GetExchange("/{todoId}")
    Todo getTodo(@PathVariable Integer todoId);

    @GetExchange
    List<Todo> getTodos();

    @PostExchange
    Todo save(@RequestBody Todo todo);
}
