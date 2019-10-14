package mr.cell.routesticker.climbingroutesservice

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang.builder.ToStringBuilder
import org.apache.commons.lang.builder.ToStringStyle
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class RouteDTO() {

    var id: UUID? = null
        @JsonProperty get() = field
        @JsonIgnore set(value) {
            field = value
        }
    var name: String? = null
    var type: RouteType? = null
    var grade: RouteGrade? = null
    var country: String? = null
    var region: String? = null
    var cragId: UUID? = null
    var cragName: String? = null
    var sectorId: UUID? = null
    var sectorName: String? = null

    constructor(route: Route) : this() {
        this.id = route.id
        this.name = route.name
        this.type = route.type
        this.grade = route.grade
        this.country = route.country
        this.region = route.region
        this.cragId = route.cragId
        this.cragName = route.cragName
        this.sectorId = route.sectorId
        this.sectorName = route.sectorName
    }

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, false)
    }
}