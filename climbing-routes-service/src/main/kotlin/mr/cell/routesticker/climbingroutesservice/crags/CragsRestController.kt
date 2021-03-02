package mr.cell.routesticker.climbingroutesservice.crags

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/crags")
class CragsRestController(val crags: CragsService) {

    @GetMapping
    fun getCrags(): Flux<CragDTO> {
        return crags.getCrags().map { CragDTO(it) }
    }

    @GetMapping("/{id}")
    fun getCrag(@PathVariable id: UUID): Mono<CragDTO> {
        return crags.getCrag(id).map { CragDTO(it) }
    }

    @PostMapping
    fun createCrag(@RequestBody crag: CragDTO): Mono<CragDTO> {
        return crags.createCrag(crag).map { CragDTO(it) }
    }

    @DeleteMapping("/{id}")
    fun deleteCrag(@PathVariable id: UUID): Mono<CragDTO> {
        return crags.deleteCrag(id).map { CragDTO(it) }
    }

    @PutMapping("/{id}")
    fun updateCrag(@PathVariable id: UUID, @RequestBody crag: CragDTO): Mono<CragDTO> {
        return crags.updateCrag(id, crag).map { CragDTO(it) }
    }
}