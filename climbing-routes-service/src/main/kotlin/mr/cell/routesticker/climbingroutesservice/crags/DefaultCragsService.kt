package mr.cell.routesticker.climbingroutesservice.crags

import mr.cell.routesticker.climbingroutesservice.ResourceNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.publisher.SynchronousSink
import reactor.core.publisher.switchIfEmpty
import java.lang.IllegalArgumentException
import java.util.*

@Service
class DefaultCragsService(val crags: CragsRespository): CragsService {

    override fun getCrag(id: UUID): Mono<Crag> {
        return crags.findById(id)
    }

    override fun getCrags(): Flux<Crag> {
        return crags.findAll()
    }

    override fun createCrag(crag: CragDTO): Mono<Crag> {
        return Mono.just(crag)
                .map { toCrag(it) }
                .doOnNext { crags.save(it) }
    }

    private fun toCrag(crag: CragDTO): Crag {
        return Crag(UUID.randomUUID(),
                crag.name ?: "",
                crag.description ?: "",
                crag.country ?: "",
                crag.region ?: "")
    }

    override fun deleteCrag(id: UUID): Mono<Crag> {
        return crags.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException(id, Crag::class.java)))
                .doOnNext { crag -> crags.delete(crag) }
    }

    override fun updateCrag(id: UUID, crag: CragDTO): Mono<Crag> {
        return crags.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException(id, Crag::class.java)))
                .zipWith(Mono.just(crag))
                .map { tuple -> merge(tuple.t1, tuple.t2) }
                .doOnNext { crags.save(it) }
    }

    private fun merge(cragToBeUpdated: Crag, crag: CragDTO): Crag {
        return Crag(cragToBeUpdated.id,
                crag.name ?: cragToBeUpdated.name,
                crag.description ?: cragToBeUpdated.description,
                crag.country ?: cragToBeUpdated.country,
                crag.region ?: cragToBeUpdated.region)
    }
}