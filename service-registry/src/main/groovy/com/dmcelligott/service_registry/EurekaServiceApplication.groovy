package com.dmcelligott.service_registry

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer

@EnableEurekaServer
@SpringBootApplication
class EurekaServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(EurekaServiceApplication.class, args)
    }
}
