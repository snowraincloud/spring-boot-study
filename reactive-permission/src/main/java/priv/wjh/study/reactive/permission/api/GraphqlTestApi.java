package priv.wjh.study.reactive.permission.api;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Controller
public class GraphqlTestApi {

    @QueryMapping
    public String test(){
        return "test: " + System.currentTimeMillis();
    }
}
