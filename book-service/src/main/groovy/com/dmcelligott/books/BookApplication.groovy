package com.dmcelligott.books

import io.opentracing.Tracer
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@SpringBootApplication
@EnableAutoConfiguration
@EnableDiscoveryClient
class BookApplication {

    Logger log = LoggerFactory.getLogger(BookApplication.class)


    @Autowired
    Tracer tracer

    @RequestMapping('/available')
    String available() {

        def response = 'Spring in Action'


        tracer.scopeManager().active().span().setTag('payload', response)
        log.info("available endpoint")
        return response
    }

    @PostMapping('/checkout')
    String checkOut(@RequestBody Book book) {
        def response = "Checked out ${book}"

        log.info("checking out book endpoint")
        return response
    }

    @RequestMapping('/checked-out')
    String checkedOut() {
        def response = 'Measure What matters'
        tracer.scopeManager().active().span().setTag('payload', response)
        log.info("checked-out endpoint")

        return response
    }

    static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args)
    }



}
