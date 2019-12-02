package mr.cell.routesticker.climbingroutesservice.routes

import com.fasterxml.jackson.annotation.JsonProperty

enum class RouteType(val value: String) {
    @JsonProperty("Sport") SPORT("Sport"),
    @JsonProperty("Boulder") BOULDER("Boulder"),
    @JsonProperty("Mountain") MOUNTAIN("Mountain"),
    @JsonProperty("") UNKNOWN("");
}
