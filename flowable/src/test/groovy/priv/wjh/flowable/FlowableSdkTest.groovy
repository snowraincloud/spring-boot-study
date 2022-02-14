package priv.wjh.flowable

import org.flowable.engine.RepositoryService
import org.flowable.engine.repository.ProcessDefinitionQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class FlowableSdkTest extends Specification{
    @Autowired
    private RepositoryService repositoryService

    def "create process"(){
        given:
        def deploymentBuilder = repositoryService.createDeployment()
                .category("leave")
        .name("请假")
        .addClasspathResource("processes/leaveApproval.bpmn20.xml")
        def deploy = deploymentBuilder.deploy()
        expect:
        println(deploy)
    }

    def "query process definition"(){
        given:
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery()
        def processDefinition = processDefinitionQuery
                .processDefinitionKeyLike("%leave%")
                .list()
        expect:
        processDefinition.size() == 1
        println(processDefinition)
    }


    def "get process image"(){
        given:
        def bpmnModel = repositoryService.getBpmnModel("leaveApproval");
        expect:
        println(bpmnModel)

    }
}
