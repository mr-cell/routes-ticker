package mr.cell.routesticker.climbingroutesservice.crags

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CragsMongoRepository: CragsRespository, ReactiveMongoRepository<Crag, UUID>