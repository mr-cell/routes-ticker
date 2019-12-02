package mr.cell.routesticker.climbingroutesservice.crags

import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.reactive.ReactiveSortingRepository
import java.util.*

@NoRepositoryBean
interface CragsRespository: ReactiveSortingRepository<Crag, UUID>