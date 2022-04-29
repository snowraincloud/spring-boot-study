package priv.wjh.test.jgit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@RestController
@RequestMapping("/time")
public class TestLocalDateTime {

    @GetMapping
    public Map<String, LocalDateTime> test() {
        Map<String, LocalDateTime> result = new HashMap<>();
        result.put("test", LocalDateTime.now());
        return result;
    }

}
