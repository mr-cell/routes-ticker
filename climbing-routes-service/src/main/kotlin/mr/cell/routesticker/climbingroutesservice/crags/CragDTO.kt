package mr.cell.routesticker.climbingroutesservice.crags

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang.builder.ToStringBuilder
import org.apache.commons.lang.builder.ToStringStyle
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class CragDTO() {
    var id: UUID? = null
        @JsonProperty get() = field
        @JsonIgnore set(value) {
            field = value
        }
    var name: String? = null
    var description: String? = null
    var country: String? = null
    var region: String? = null

    constructor(crag: Crag): this() {
        this.id = crag.id
        this.name = crag.name
        this.description = crag.description
        this.country = crag.country
        this.region = crag.region
    }

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, false)
    }
}