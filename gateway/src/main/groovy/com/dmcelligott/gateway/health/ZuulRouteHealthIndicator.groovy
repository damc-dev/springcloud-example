package com.dmcelligott.gateway.health


import org.springframework.boot.actuate.health.AbstractHealthIndicator
import org.springframework.boot.actuate.health.Health
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient

class ZuulRouteHealthIndicator extends AbstractHealthIndicator {

    private String serviceId
    private DiscoveryClient discoveryClient

    ZuulRouteHealthIndicator(String serviceId, DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient
        this.serviceId = serviceId
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        builder.withDetail("serviceId", serviceId)
        builder.withDetail("description", "Ensures service instances (that Zuul routes to) are available")
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId)
        builder.withDetail("serviceInstanceCount", serviceInstances.size())
        if (serviceInstances) {
            builder.up().withDetail("serviceInstances", serviceInstances)
        } else {
            builder.down().withDetail("rootCause", "No service instances found")
        }
    }
}
