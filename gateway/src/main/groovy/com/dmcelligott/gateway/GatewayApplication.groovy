package com.dmcelligott.gateway

import com.dmcelligott.gateway.filters.pre.SimpleFilter
import io.jaegertracing.Configuration
import io.opentracing.Tracer
import io.opentracing.contrib.spring.cloud.zuul.TracePreZuulFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
class GatewayApplication {

    @Value('${spring.application.name}')
    private String appName

    static void main(def args) {
        SpringApplication.run(GatewayApplication.class, args)
    }

    @Bean
    SimpleFilter simpleFilter() {
        return new SimpleFilter()
    }

    @Bean
    Tracer tracer() {
        Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration
                .fromEnv()
                .withType("const")
                .withParam(1)
        Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration
                .fromEnv()
                .withLogSpans(true)
        Configuration config = new Configuration(appName)
                .withSampler(samplerConfig)
                .withReporter(reporterConfig)
        return config.getTracer()
    }

}
