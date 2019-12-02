package mr.cell.routesticker.climbingroutesservice.crags

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

interface CragsService {
    fun getCrag(id: UUID): Mono<Crag>
    fun getCrags(): Flux<Crag>
    fun createCrag(crag: CragDTO): Mono<Crag>
    fun deleteCrag(id: UUID): Mono<Crag>
    fun updateCrag(id: UUID, crag: CragDTO): Mono<Crag>
}