package mr.cell.routesticker.climbingroutesservice.routes

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
        @JsonProperty get() = field
        @JsonIgnore set(value) {
            field = value
        }
    var region: String? = null
        @JsonProperty get() = field
        @JsonIgnore set(value) {
            field = value
        }
    var cragId: UUID? = null
        @JsonProperty get() = field
        @JsonIgnore set(value) {
            field = value
        }
    var cragName: String? = null
        @JsonProperty get() = field
        @JsonIgnore set(value) {
            field = value
        }
    var sectorId: UUID? = null
        @JsonProperty get() = field
        @JsonIgnore set(value) {
            field = value
        }
    var sectorName: String? = null
        @JsonProperty get() = field
        @JsonIgnore set(value) {
            field = value
        }

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