package mr.cell.routesticker.climbingroutesservice.routes

import mr.cell.routesticker.climbingroutesservice.ResourceNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import java.util.UUID

@Service
class DefaultRoutesService(val routes: RoutesRepository): RoutesService {
    companion object {
        const val NO_CRAG_ID = "Mandatory field 'cragId' has not been submitted"
        const val NO_SECTOR_ID = "Mandatory field 'sectorId' has not been submitted"
    }

    override fun getRoute(id: UUID): Mono<Route> {
        return routes.findById(id)
    }

    override fun getRoutes(): Flux<Route> {
        return routes.findAll()
    }

    override fun createRoute(route: RouteDTO): Mono<Route> {
        return Mono.just(route)
                .handle { route, sink: SynchronousSink<Route> ->
                    try {
                        val routeToBeSaved = toRoute(route)
                        sink.next(routeToBeSaved)
                    } catch (ex: IllegalArgumentException) {
                        sink.error(ex)
                    }
                }.doOnNext { routes.save(it) }
    }

    private fun toRoute(route: RouteDTO): Route {
        return Route(UUID.randomUUID(),
                route.name ?: "",
                route.type ?: RouteType.UNKNOWN,
                route.grade ?: RouteGrade.UNKNOWN,
                route.country ?: "",
                route.region ?: "",
                route.cragId ?: throw IllegalArgumentException(NO_CRAG_ID),
                route.cragName ?: "",
                route.sectorId ?: throw IllegalArgumentException(NO_SECTOR_ID),
                route.sectorName ?: "")
    }

    override fun deleteRoute(id: UUID): Mono<Route> {
        return routes.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException(id, Route::class.java)))
                .doOnNext { route -> routes.delete(route) }
    }

    override fun updateRoute(id: UUID, route: RouteDTO): Mono<Route> {
        return routes.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException(id, Route::class.java)))
                .zipWith(Mono.just(route))
                .map { tuple -> merge(tuple.t1, tuple.t2) }
                .doOnNext { routes.save(it) }
    }

    private fun merge(routeToBeUpdated: Route, route: RouteDTO): Route {
        return Route(routeToBeUpdated.id,
                route.name ?: routeToBeUpdated.name,
                route.type ?: routeToBeUpdated.type,
                route.grade ?: routeToBeUpdated.grade,
                route.country ?: routeToBeUpdated.country,
                route.region ?: routeToBeUpdated.region,
                route.cragId ?: routeToBeUpdated.cragId,
                route.cragName ?: routeToBeUpdated.cragName,
                route.sectorId ?: routeToBeUpdated.sectorId,
                route.sectorName ?: routeToBeUpdated.sectorName)
    }
}