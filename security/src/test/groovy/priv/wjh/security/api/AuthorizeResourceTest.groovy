package priv.wjh.security.api

import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.context.TypeExcludeFilter
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import priv.wjh.security.application.AuthApplicationService
import priv.wjh.security.domain.auth.User
import priv.wjh.security.infrastructure.security.SecurityConfig
import spock.lang.Ignore
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(value = AuthorizeResource.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
class AuthorizeResourceTest extends Specification {

    @Autowired
    MockMvc mockMvc
    @SpringBean
    AuthApplicationService authApplicationService = Stub()

    def "Test for login"() {
        given:
        def mapper = new ObjectMapper()
        def user = new User()

        when:
        authApplicationService.login(_) >> jwt

        then:
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user)))
                .andExpect(s)
                .andExpect(content().string(c))

        where:
        jwt                             || s                   | c
        Optional.ofNullable(null)       || { status().isOk() } | "Failed to login"
        Optional.of("Login Successful") || { status().isOk() } | "Login Successful"

    }

    @SpringBootConfiguration
    @ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class))
    static class config {
    }

    @Ignore
    def "print password encode"() {
        def passwordEncoder = SecurityConfig.passwordEncoder()
        println(password + " : " + passwordEncoder.encode(password))
        where:
        password << ["test123"]

    }


}
