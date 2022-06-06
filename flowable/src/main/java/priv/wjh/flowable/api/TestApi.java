package priv.wjh.flowable.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestApi {
    private final RepositoryService repositoryService;

    @GetMapping("/definition")
    public Integer getDefinition() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        var processDefinition = processDefinitionQuery
                .processDefinitionKeyLike("%leave%")
                .list();
        log.info(String.valueOf(processDefinition.size()));
        return processDefinition.size();
    }
}
