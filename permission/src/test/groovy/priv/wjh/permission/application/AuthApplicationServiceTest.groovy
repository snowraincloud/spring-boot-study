package priv.wjh.permission.application


import priv.wjh.permission.domain.permission.po.PermissionPojo
import priv.wjh.permission.domain.permission.service.impl.AuthServiceImpl
import spock.lang.Specification

class AuthApplicationServiceTest extends Specification {
    def "GetMenu"() {
        given:
        def authService = Mock(AuthServiceImpl)
        def authApplicationService = new AuthApplicationService(authService)
        when:
        authService.getTreePermission() >> new ArrayList(new HashSet(getPermissions()))
        def res = authApplicationService.getMenu()
        then:
        res.size() == 10
        res.forEach(menu -> {
            println(menu)
            menu.children.forEach(menu1 -> {
                println(menu1)
            })
        })
    }

    def getPermissions() {
        def res = new ArrayList<PermissionPojo>()
        for (i in 1..10) {
            def tmp = new PermissionPojo()
            tmp.id = i
            tmp.pid = 0
            tmp.type = 0
            res.add(tmp)
        }
        for (i in 11..20) {
            def tmp = new PermissionPojo()
            tmp.id = i
            tmp.pid = i - 10
            tmp.type = 1
            res.add(tmp)
        }
        return res
    }
}
