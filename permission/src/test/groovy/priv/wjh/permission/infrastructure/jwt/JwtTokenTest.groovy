package priv.wjh.permission.infrastructure.jwt

import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.function.Supplier
/**
 * 负载相同, token会重复
 */
class JwtTokenTest extends Specification {

    def "generate token"(){
        given:
        def jwtProperties = Stub(JwtProperties)
        def jwtService = new JwtService(jwtProperties);
        jwtProperties.getExpireTime() >> 1000*60*30*10000;
        jwtProperties.getSecret() >> "123456"
        expect:
        println(jwtService.generateAccessToken("1"))
    }


    def "test for java-jwt createToken"(){
        given:
        def jwtProperties = Stub(JwtProperties)
        def jwtService = new JwtService(jwtProperties);
        when:
        jwtProperties.getExpireTime() >> 1000*60*30*10000;
        jwtProperties.getSecret() >> "123456"
        and:
        def res = generateToken(count, () -> jwtService.generateAccessToken("1"))
        then:
        res == count
        where:
        count | ignore
        10    | 0
        20    | 0
        30    | 0
        50    | 0
        100   | 0
        500   | 0
    }


    def "test"(){
        given:

        Collection<Integer> list = new HashSet<>()
        for (int i = 1; i < 100000; i++ ){
            list.add(i)
        }
        def pre = System.currentTimeMillis()
        for (int i = 1; i < 100000; i++ ){
            list.contains(i)
        }
        println(System.currentTimeMillis() - pre)
    }



    def "test for jjwt createToken"() {
        given:
        JwtToken jwtToken = new JwtToken()
        when:
        def res = generateToken(count, () -> jwtToken.createToken("1"))
        then:
        res == count
        where:
        count | ignore
        10    | 0
        20    | 0
        30    | 0
        50    | 0
        100   | 0
        500   | 0
        1000  | 0
    }


    def generateToken(int count, Supplier<String> supplier){
        CountDownLatch countDownLatch = new CountDownLatch(count)
        Set<String> set = new HashSet<>(count)
        List<Thread> threadList = new ArrayList<>(count)
        for (int i = 0; i < count; i++) {
            def thread = new Thread(() -> {
                countDownLatch.countDown()
                countDownLatch.await()
                def token = supplier.get()
                set.add(token)
            })
            thread.start()
            threadList.add(thread)
        }
        threadList.stream().forEach(thread -> thread.join())
        return set.size()
    }

}
