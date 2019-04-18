package com.dmcelligott.gateway.health

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.health.CompositeHealthIndicator
import org.springframework.boot.actuate.health.HealthAggregator
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class CompositeZuulRouteHealthIndicatorConfig {

    @Autowired
    HealthAggregator healthAggregator

    @Autowired
    ZuulProperties zuulProperties

    @Autowired
    Environment environment

    @Autowired
    DiscoveryClient discoveryClient

    @RefreshScope
    @Bean
    CompositeHealthIndicator compositeServiceHealthIndicator() {
        Map<String, HealthIndicator> indicators = new HashMap<>()
        for(String serviceId: getServiceIds()) {
            indicators.put(serviceId, new ZuulRouteHealthIndicator(serviceId, discoveryClient))
        }
        new CompositeHealthIndicator(healthAggregator, indicators)
    }

    Collection<String> getServiceIds() {
        Collection<ZuulProperties.ZuulRoute> zuulRoutes = zuulProperties.getRoutes().values()

        Collection<ZuulProperties.ZuulRoute> zuulRoutesWithoutServerList = zuulRoutes.findAll { ZuulProperties.ZuulRoute route ->
            String serviceId = route.getServiceId()
            String listOfServers = environment.getProperty("${serviceId}.ribbon.listOfServers")
            return !listOfServers
        }
        return zuulRoutesWithoutServerList.collect { ZuulProperties.ZuulRoute route -> route.getServiceId() }
    }
}
