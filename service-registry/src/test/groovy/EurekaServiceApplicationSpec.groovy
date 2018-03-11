import com.dmcelligott.service_registry.EurekaServiceApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ContextConfiguration(classes = EurekaServiceApplication.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
class EurekaServiceApplicationSpec extends Specification {

    Logger log = LoggerFactory.getLogger(EurekaServiceApplicationSpec.class);

    @Autowired
    private TestRestTemplate restTemplate

    def '/eureka/apps should return catalog'() {
        when:
            ResponseEntity<Map> entity = restTemplate.getForEntity('/eureka/apps', Map.class)
            log.info('Apps registered: {}', entity.body)
        then:
            entity.getStatusCode() == HttpStatus.OK
    }
}
