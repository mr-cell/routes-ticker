package mr.cell.routesticker.gatewayservice;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackRestController {

    @RequestMapping("/fallback")
    public String fallback() {
        return "Fallback";
    }
}
