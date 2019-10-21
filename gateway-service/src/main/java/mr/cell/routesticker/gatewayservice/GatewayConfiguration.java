package mr.cell.routesticker.gatewayservice;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator gatewayRoutes(final RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/users")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("usersFallback")
                                        .setFallbackUri("forward:/fallback/users")
                                        .setSetter(HystrixObservableCommand.Setter
                                                .withGroupKey(createHystrixCommandGroupKey("routes-ticker"))
                                                .andCommandKey(createHystrixCommandKey("users")))))
                        .uri("lb://usersservice/users"))
                .route(p -> p
                        .path("/routes")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("routesFallback")
                                        .setFallbackUri("forward:/fallback/routes")
                                        .setSetter(HystrixObservableCommand.Setter
                                                .withGroupKey(createHystrixCommandGroupKey("routes-ticker"))
                                                .andCommandKey(createHystrixCommandKey("routes")))))
                        .uri("lb://climbingroutesservice/routes"))
                .build();
    }

    private HystrixCommandGroupKey createHystrixCommandGroupKey(final String key) {
        return HystrixCommandGroupKey.Factory.asKey(key);
    }

    private HystrixCommandKey createHystrixCommandKey(final String key) {
        return HystrixCommandKey.Factory.asKey(key);
    }
}
