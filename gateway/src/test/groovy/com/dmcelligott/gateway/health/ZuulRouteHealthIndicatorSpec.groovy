package com.dmcelligott.gateway.health


import org.springframework.boot.actuate.health.Status
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryProperties
import spock.lang.Specification


class ZuulRouteHealthIndicatorSpec extends Specification {

    def 'When no instances found for serviceId then health should be DOWN'() {
        given:
        DiscoveryClient discoveryClient = Mock(DiscoveryClient)
        String serviceId = "sha_eligibles_service"
        ZuulRouteHealthIndicator zuulRouteHealthIndicator = new ZuulRouteHealthIndicator(serviceId, discoveryClient)
        when: "Discovery client returns no instances for serviceId"
        discoveryClient.getInstances(serviceId) >> {
            return new ArrayList<ServiceInstance>()
        }
        then: "Health is reported as down"
        zuulRouteHealthIndicator.health().status == Status.DOWN
    }

    def 'When instances found for serviceId then health should be UP'() {
        given:
        DiscoveryClient discoveryClient = Mock(DiscoveryClient)
        String serviceId = "sha_eligibles_service"
        ZuulRouteHealthIndicator zuulRouteHealthIndicator = new ZuulRouteHealthIndicator(serviceId, discoveryClient)
        when: "Discovery client returns no instances for serviceId"
        discoveryClient.getInstances(serviceId) >> {
            return Arrays.asList(
                    new SimpleDiscoveryProperties.SimpleServiceInstance(
                            new URI("https://lx12345:8081")))
        }
        then: "Health is reported as down"
        zuulRouteHealthIndicator.health().status == Status.UP
    }
}
