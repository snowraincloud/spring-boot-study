package priv.wjh.security

import spock.lang.Specification


class HelloSpockSpec extends Specification {

    def "length of spock's and his friends' names"() {
        expect:
        name.size() == length


        where:
        name     || length
        "Spock"  || 5
        "Kirk"   || 4
        "Scotty" || 6
    }

}