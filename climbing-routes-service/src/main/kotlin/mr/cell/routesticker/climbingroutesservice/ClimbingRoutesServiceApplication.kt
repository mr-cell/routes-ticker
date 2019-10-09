package mr.cell.routesticker.climbingroutesservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@EnableEurekaClient
@SpringBootApplication
class ClimbingRoutesServiceApplication

fun main(args: Array<String>) {
	runApplication<ClimbingRoutesServiceApplication>(*args)
}
