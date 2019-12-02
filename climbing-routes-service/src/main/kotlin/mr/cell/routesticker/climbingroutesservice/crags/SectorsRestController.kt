package mr.cell.routesticker.climbingroutesservice.crags

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/crags")
class SectorsRestController {

    @GetMapping("/{cragId}/sectors")
    fun getSectors(@PathVariable cragId: UUID): Flux<Any> {
        TODO()
    }

    @GetMapping("/{cragId}/sectors/{sectorId}")
    fun getSector(@PathVariable cragId: UUID, @PathVariable sectorId: UUID) : Mono<Any> {
        TODO()
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{cragId}/sectors")
    fun createSector(@PathVariable cragId: UUID, @RequestBody sector: Any): Mono<Any> {
        TODO()
    }

    @DeleteMapping("/{cragId}/sectors/{sectorId}")
    fun deleteSector(@PathVariable cragId: UUID, @PathVariable sectorId: UUID): Mono<Any> {
        TODO()
    }

    @PutMapping("/{cragId}/sectors/{sectorId}")
    fun updateSector(@PathVariable cragId: UUID, @PathVariable sectorId: UUID, sector: Any): Mono<Any> {
        TODO()
    }

}