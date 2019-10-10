package mr.cell.routesticker.climbingroutesservice

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import java.util.UUID

@NoRepositoryBean
interface RoutesRepository: ReactiveSortingRepository<Route, UUID> {

}