package priv.wjh.security.infrastructure.service


import priv.wjh.security.infrastructure.jwt.JwtProperties
import priv.wjh.security.infrastructure.jwt.JwtService
import spock.lang.Specification

class JwtServiceTest extends Specification {

    def "test for jwt service"(){
        def jwtProperties = Stub(JwtProperties)
        def jwtService = new JwtService(jwtProperties);

        given:
        jwtProperties.getExpireTime() >> 1000
        jwtProperties.getSecret() >> "dwadwa"

        when:
        def token = jwtService.generateAccessToken("test")
        def test = jwtService.parserAccessToken(token)

        then:
        test.empty
    }

}
