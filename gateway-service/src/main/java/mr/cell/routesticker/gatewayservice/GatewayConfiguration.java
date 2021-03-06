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

    private static final String HYSTRIX_COMMAND_GROUP_KEY = "routes-ticker";

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
                                                .withGroupKey(createHystrixCommandGroupKey(HYSTRIX_COMMAND_GROUP_KEY))
                                                .andCommandKey(createHystrixCommandKey("users")))))
                        .uri("lb://usersservice/users"))
                .route(p -> p
                        .path("/routes")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("routesFallback")
                                        .setFallbackUri("forward:/fallback/routes")
                                        .setSetter(HystrixObservableCommand.Setter
                                                .withGroupKey(createHystrixCommandGroupKey(HYSTRIX_COMMAND_GROUP_KEY))
                                                .andCommandKey(createHystrixCommandKey("routes")))))
                        .uri("lb://climbingroutesservice/routes"))
                .route(p -> p
                        .path("/crags")
                        .filters(f -> f
                                .hystrix(config -> config
                                        .setName("cragsFallback")
                                        .setFallbackUri("forward:/fallback/crags")
                                        .setSetter(HystrixObservableCommand.Setter
                                                .withGroupKey(createHystrixCommandGroupKey(HYSTRIX_COMMAND_GROUP_KEY))
                                                .andCommandKey(createHystrixCommandKey("crags")))))
                        .uri("lb://climbingroutesservice/crags"))
                .build();
    }

    private HystrixCommandGroupKey createHystrixCommandGroupKey(final String key) {
        return HystrixCommandGroupKey.Factory.asKey(key);
    }

    private HystrixCommandKey createHystrixCommandKey(final String key) {
        return HystrixCommandKey.Factory.asKey(key);
    }
}
