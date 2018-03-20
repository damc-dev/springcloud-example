package com.dmcelligott.books

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.hystrix.EnableHystrix
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableHystrix
class BookApplication {

    @Autowired
    RestTemplate restTemplate

    Logger log = LoggerFactory.getLogger(BookApplication.class)

    @RequestMapping('/available')
    @ResponseBody
    String available() {
        log.info("available endpoint")
        return 'Spring in Action'
    }

    @RequestMapping('/checked-out')
    @ResponseBody
    String checkedOut() {
        log.info("checked-out endpoint")
        return 'Spring in Action'
    }

    @RequestMapping('/recommendations')
    @ResponseBody
    String recommendations() {
        log.info("recommendations endpoint")
        ResponseEntity<String> entity = restTemplate.getForEntity("http://book-recommendation-service/recommended", String.class)
        return entity.getBody()
    }

    static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args)
    }


}
