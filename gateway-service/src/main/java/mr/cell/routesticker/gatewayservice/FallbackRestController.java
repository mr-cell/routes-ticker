package mr.cell.routesticker.gatewayservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackRestController {

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("Fallback");
    }
}
