package com.dmcelligott.gateway

import com.dmcelligott.gateway.filters.pre.SimpleFilter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
class GatewayApplication {

    static void main(def args) {
        SpringApplication.run(GatewayApplication.class, args)
    }

    @Bean
    SimpleFilter simpleFilter() {
        return new SimpleFilter()
    }
}
