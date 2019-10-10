package mr.cell.routesticker.climbingroutesservice

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import java.util.UUID

@Service
class RoutesServiceImpl(val routes: RoutesRepository): RoutesService {
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

    override fun saveRoute(route: RouteDTO): Mono<Route> {
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

    fun toRoute(route: RouteDTO): Route {
        return Route(UUID.randomUUID(),
                route.routeName ?: "Unknown",
                route.routeType ?: RouteType.UNKNOWN,
                route.routeGrade ?: RouteGrade.UNKNOWN,
                route.country ?: "Unknown",
                route.region ?: "Unknown",
                route.cragId ?: throw IllegalArgumentException(NO_CRAG_ID),
                route.cragName ?: "Unknown",
                route.sectorId ?: throw IllegalArgumentException(NO_SECTOR_ID),
                route.sectorName ?: "Unknown")
    }

    override fun deleteRoute(id: UUID): Mono<Route> {
        return routes.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException(id, Route::class.java)))
                .doOnNext { routes.delete(it) }
    }

    override fun updateRoute(id: UUID, route: RouteDTO): Mono<Route> {
        return routes.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException(id, Route::class.java)))
                .zipWith(Mono.just(route))
                .map { tuple ->
                    val routeToBeUpdated = tuple.t1
                    val route = tuple.t2
                    Route(routeToBeUpdated.routeId,
                            route.routeName ?: routeToBeUpdated.routeName,
                            route.routeType ?: routeToBeUpdated.routeType,
                            route.routeGrade ?: routeToBeUpdated.routeGrade,
                            route.country ?: routeToBeUpdated.country,
                            route.region ?: routeToBeUpdated.region,
                            route.cragId ?: routeToBeUpdated.cragId,
                            route.cragName ?: routeToBeUpdated.cragName,
                            route.sectorId ?: routeToBeUpdated.sectorId,
                            route.sectorName ?: routeToBeUpdated.sectorName)
                }.doOnNext { route: Route -> routes.save(route) }
    }
}