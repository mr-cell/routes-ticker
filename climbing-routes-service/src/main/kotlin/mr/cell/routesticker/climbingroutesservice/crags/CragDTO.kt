package mr.cell.routesticker.climbingroutesservice.crags

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
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
}