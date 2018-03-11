package com.dmcelligott.books

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.Specification

@ContextConfiguration(classes = BookApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookApplicationSpec extends Specification {

    Logger log = LoggerFactory.getLogger(BookApplicationSpec.class)

    @Autowired
    TestRestTemplate testRestTemplate

    @Ignore
    def '/health reports UP'() {
        when:
        ResponseEntity<Map> entity = testRestTemplate.getForEntity('/health', Map.class)
        then:
        log.info("reponse: {}", entity.getBody())
        entity.getStatusCode() == HttpStatus.OK

    }

}
