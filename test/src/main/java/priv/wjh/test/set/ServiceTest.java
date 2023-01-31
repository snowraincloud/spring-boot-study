package priv.wjh.test.set;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
public class ServiceTest {

    public void test(){

        DoTest doTest = new DoTest();
        AoTest ao = new AoTest();
        ao.setId(doTest.getId());
        ao.setName(doTest.getName());

    }
}
