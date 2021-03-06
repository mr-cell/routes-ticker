package mr.cell.routesticker.climbingroutesservice.routes

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface RoutesMongoRepository: RoutesRepository, ReactiveMongoRepository<Route, UUID> {
}