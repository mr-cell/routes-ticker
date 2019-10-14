package mr.cell.routesticker.climbingroutesservice

import com.fasterxml.jackson.annotation.JsonProperty

enum class RouteGrade(val value: String) {
    @JsonProperty("") UNKNOWN(""),
    @JsonProperty("2") GRADE_2("2"),
    @JsonProperty("3a") GRADE_3a("3a"),
    @JsonProperty("3b") GRADE_3b("3b"),
    @JsonProperty("3c") GRADE_3c("3c"),
    @JsonProperty("4a") GRADE_4a("4a"),
    // ...
    // TODO

}
