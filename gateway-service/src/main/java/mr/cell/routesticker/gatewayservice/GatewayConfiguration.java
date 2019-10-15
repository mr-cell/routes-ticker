package mr.cell.routesticker.gatewayservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Autowired
    DiscoveryClient discoveryClient;

    @Bean
    public RouteLocator gatewayRoutes(final RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/users").uri("lb://usersservice/users"))
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p
                        .host("*.hystrix.com")
                        .filters(f -> f.hystrix(config -> config
                                .setName("mycmd")
                                .setFallbackUri("forward:/fallback")))
                        .uri("http://httpbin.org:80"))
                .build();
    }
}
