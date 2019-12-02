package mr.cell.routesticker.climbingroutesservice.crags

import mr.cell.routesticker.climbingroutesservice.ResourceNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Service
class CragsServiceImpl(val crags: CragsRespository): CragsService {

    override fun getCrag(id: UUID): Mono<Crag> {
        return crags.findById(id)
    }

    override fun getCrags(): Flux<Crag> {
        return crags.findAll()
    }

    override fun createCrag(crag: CragDTO): Mono<Crag> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteCrag(id: UUID): Mono<Crag> {
        return crags.findById(id)
                .switchIfEmpty(Mono.error(ResourceNotFoundException(id, Crag::class.java)))
                .doOnNext { crag -> crags.delete(crag) }
    }

    override fun updateCrag(id: UUID, crag: CragDTO): Mono<Crag> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}