package mr.cell.routesticker.climbingroutesservice.crags

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.commons.lang.builder.ToStringBuilder
import org.apache.commons.lang.builder.ToStringStyle
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class SectorDTO {

    var id: UUID? = null
        @JsonProperty get() = field
        @JsonIgnore set(value) {
            field = value
        }
    var name: String? = null
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

    override fun toString(): String {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE, false)
    }
}
