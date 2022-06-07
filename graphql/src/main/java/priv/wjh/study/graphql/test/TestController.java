package priv.wjh.study.graphql.test;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Controller
public class TestController {
    @QueryMapping
    public String test(){
        return "test: " + System.currentTimeMillis();
    }
}
