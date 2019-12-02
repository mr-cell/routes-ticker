package mr.cell.routesticker.climbingroutesservice.routes

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
@TypeAlias("route")
data class Route(
        @Id val id: UUID,
        val name: String,
        val type: RouteType,
        val grade: RouteGrade,
        val country: String,
        val region: String,
        val cragId: UUID,
        val cragName: String,
        val sectorId: UUID,
        val sectorName: String)