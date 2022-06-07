package priv.wjh.security.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@RestController
public class TestApi {

    @PostMapping
    public String test(){
        return "1111";
    }

}
