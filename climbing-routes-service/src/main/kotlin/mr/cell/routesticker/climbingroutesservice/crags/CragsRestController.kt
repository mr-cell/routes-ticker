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
        TODO()
    }

    @GetMapping("/{id}")
    fun getCrag(@PathVariable id: UUID): Mono<CragDTO> {
        TODO()
    }

    @PostMapping
    fun createCrag(@RequestBody crag: CragDTO): Mono<CragDTO> {
        TODO()
    }

    @DeleteMapping("/{id}")
    fun deleteCrag(@PathVariable id: UUID): Mono<CragDTO> {
        TODO()
    }

    @PutMapping("/{id}")
    fun updateCrag(@PathVariable id: UUID, @RequestBody crag: CragDTO): Mono<CragDTO> {
        TODO()
    }
}