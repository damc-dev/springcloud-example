package com.dmcelligott.books

import io.opentracing.Tracer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import javax.servlet.Filter

@Configuration
class TraceConfig {


    @Value('${spring.application.name}')
    private String appName

    @Bean
    Tracer tracer() {
        io.jaegertracing.Configuration.SamplerConfiguration samplerConfig = io.jaegertracing.Configuration.SamplerConfiguration
                .fromEnv()
                .withType("const")
                .withParam(1)
        io.jaegertracing.Configuration.ReporterConfiguration reporterConfig = io.jaegertracing.Configuration.ReporterConfiguration
                .fromEnv()
                .withLogSpans(true)
        io.jaegertracing.Configuration config = new io.jaegertracing.Configuration(appName)
                .withSampler(samplerConfig)
                .withReporter(reporterConfig)
        return config.getTracer()
    }

    @Bean
    Filter loggingFilter(Tracer tracer) {
        return new LoggingFilter(tracer)
    }

}
