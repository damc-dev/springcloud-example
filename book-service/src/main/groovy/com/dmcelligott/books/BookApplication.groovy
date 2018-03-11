package com.dmcelligott.books

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

    @RequestMapping('/available')
    String available() {
        return 'Spring in Action'
    }

    @RequestMapping('/checked-out')
    String checkedOut() {
        return 'Spring in Action'
    }

    static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args)
    }


}
