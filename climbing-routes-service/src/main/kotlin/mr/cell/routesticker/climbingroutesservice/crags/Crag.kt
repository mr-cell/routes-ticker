package mr.cell.routesticker.climbingroutesservice.crags

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
@TypeAlias("crag")
data class Crag(
        @Id val id: UUID,
        val name: String,
        val description: String,
        val country: String,
        val region: String
)