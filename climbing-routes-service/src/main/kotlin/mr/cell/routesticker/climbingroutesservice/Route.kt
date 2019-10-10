package mr.cell.routesticker.climbingroutesservice

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
@TypeAlias("route")
data class Route(
        @Id val routeId: UUID,
        val routeName: String,
        val routeType: RouteType,
        val routeGrade: RouteGrade,
        val country: String,
        val region: String,
        val cragId: UUID,
        val cragName: String,
        val sectorId: UUID,
        val sectorName: String)