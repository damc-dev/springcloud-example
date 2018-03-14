package com.dmcelligott.books

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
class BookApplication {

    Logger log = LoggerFactory.getLogger(BookApplication.class)

    @RequestMapping('/available')
    String available() {
        log.info("available endpoint")
        return 'Spring in Action'
    }

    @RequestMapping('/checked-out')
    String checkedOut() {
        log.info("checked-out endpoint")
        return 'Spring in Action'
    }

    static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args)
    }


}
