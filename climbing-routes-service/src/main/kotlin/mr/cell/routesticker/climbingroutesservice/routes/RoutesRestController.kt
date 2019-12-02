package mr.cell.routesticker.climbingroutesservice.routes

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID

@RestController
@RequestMapping("/routes")
class RoutesRestController(val routes: RoutesService) {

    @GetMapping
    fun getRoutes(): Flux<RouteDTO> {
        return routes.getRoutes().map { RouteDTO(it) }
    }

    @GetMapping("/{id}")
    fun getRoute(@PathVariable id: UUID): Mono<RouteDTO> {
        return routes.getRoute(id).map { RouteDTO(it) }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createRoute(@RequestBody route: RouteDTO): Mono<RouteDTO> {
        return routes.createRoute(route).map { RouteDTO(it) }
    }

    @DeleteMapping("/{id}")
    fun deleteRoute(@PathVariable id: UUID): Mono<RouteDTO> {
        return routes.deleteRoute(id).map { RouteDTO(it) }
    }

    @PutMapping("/{id}")
    fun updateRoute(@PathVariable id: UUID, @RequestBody route: RouteDTO): Mono<RouteDTO> {
        return routes.updateRoute(id, route).map { RouteDTO(it) }
    }
}