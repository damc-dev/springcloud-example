package com.dmcelligott.books.recommend

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
class BookRecommendationApplication {

    Logger log = LoggerFactory.getLogger(this.class)

    @RequestMapping("/recommended")
    @ResponseBody
    String recommendedBooks() {
        log.info("Returning recommended books")
        return "Spring in Action (Manning), Cloud Native Java (O'Reilly), Learning Spring Boot (Packt)"
    }

    static void main(String[] args) {
        SpringApplication.run(BookRecommendationApplication.class, args)
    }

}