package mr.cell.routesticker.climbingroutesservice.routes

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import java.util.UUID

@NoRepositoryBean
interface RoutesRepository: ReactiveSortingRepository<Route, UUID>