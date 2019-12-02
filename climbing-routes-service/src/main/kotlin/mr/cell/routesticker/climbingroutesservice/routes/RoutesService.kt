package mr.cell.routesticker.climbingroutesservice.routes

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface RoutesService {
    fun getRoute(id: UUID): Mono<Route>
    fun getRoutes(): Flux<Route>
    fun createRoute(route: RouteDTO): Mono<Route>
    fun deleteRoute(id: UUID): Mono<Route>
    fun updateRoute(id: UUID, route: RouteDTO): Mono<Route>
}