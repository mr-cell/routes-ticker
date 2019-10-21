package mr.cell.routesticker.gatewayservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/fallback")
public class FallbackRestController {
    private static final Logger LOG = LoggerFactory.getLogger(FallbackRestController.class);

    @RequestMapping("/users")
    public Flux<Void> usersFallback() {
        LOG.warn("Invoking fallback for /users endpoint");
        return Flux.empty();
    }

    @RequestMapping("/routes")
    public Flux<Void> routesFallback() {
        LOG.warn("Invoking fallback for /routes endpoint");
        return Flux.empty();
    }
}
