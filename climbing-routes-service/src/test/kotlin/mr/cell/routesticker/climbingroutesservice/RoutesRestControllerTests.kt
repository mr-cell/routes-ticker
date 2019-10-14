package mr.cell.routesticker.climbingroutesservice

import com.nhaarman.mockitokotlin2.any
import net.minidev.json.JSONArray
import org.hamcrest.Matchers
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.UUID


@RunWith(SpringRunner::class)
@WebFluxTest
class RoutesRestControllerTests {
    private val log: Logger = LoggerFactory.getLogger(RoutesRestControllerTests::class.java)

    @MockBean
    private lateinit var routesService: RoutesService

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun testGetAllRoutes() {
        // given
        val routes = listOf("route1", "route2", "route3").map { createRoute(it) }
        Mockito.`when`(routesService.getRoutes()).thenReturn(Flux.fromIterable(routes))

        // when
        webTestClient.get()
                .uri("/routes")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                // then
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$..id").value(Matchers.hasSize<JSONArray>(routes.size))
                .jsonPath("$..name").value(Matchers.hasSize<JSONArray>(routes.size))
                .jsonPath("$..type").value(Matchers.hasSize<JSONArray>(routes.size))
                .jsonPath("$..grade").value(Matchers.hasSize<JSONArray>(routes.size))
                .jsonPath("$..country").value(Matchers.hasSize<JSONArray>(routes.size))
                .jsonPath("$..region").value(Matchers.hasSize<JSONArray>(routes.size))
                .jsonPath("$..cragId").value(Matchers.hasSize<JSONArray>(routes.size))
                .jsonPath("$..cragName").value(Matchers.hasSize<JSONArray>(routes.size))
                .jsonPath("$..sectorId").value(Matchers.hasSize<JSONArray>(routes.size))
                .jsonPath("$..sectorName").value(Matchers.hasSize<JSONArray>(routes.size))
    }

    @Test
    fun testGetRoute() {
        // given
        val id = UUID.randomUUID()
        val route = createRoute(id, "route")
        Mockito.`when`(routesService.getRoute(id)).thenReturn(Mono.just(route))

        // when
        webTestClient.get()
                .uri("/routes/${id}")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                // then
                .expectStatus().isOk
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").value(Matchers.`is`(route.id.toString()))
                .jsonPath("$.name").value(Matchers.`is`(route.name))
                .jsonPath("$.type").value(Matchers.`is`(route.type.value))
                .jsonPath("$.grade").value(Matchers.`is`(route.grade.value))
                .jsonPath("$.country").value(Matchers.`is`(route.country))
                .jsonPath("$.region").value(Matchers.`is`(route.region))
                .jsonPath("$.cragId").value(Matchers.`is`(route.cragId.toString()))
                .jsonPath("$.cragName").value(Matchers.`is`(route.cragName))
                .jsonPath("$.sectorId").value(Matchers.`is`(route.sectorId.toString()))
                .jsonPath("$.sectorName").value(Matchers.`is`(route.sectorName))
    }

    @Test
    fun testGetRouteByNonexistentId() {
        // given
        val id = UUID.randomUUID()
        Mockito.`when`(routesService.getRoute(id)).thenThrow(ResourceNotFoundException(id, Route::class.java))

        // when
        webTestClient.get()
                .uri("/routes/${id}")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                // then
                .expectStatus().isNotFound
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.message").value(Matchers.containsString(id.toString()))
                .jsonPath("$.timestamp").isNumber
                .jsonPath("$.path").isEqualTo("/routes/${id}")
    }

    @Test
    fun testGetRouteByInvalidId() {
        // given
        // when
        webTestClient.get()
                .uri("/routes/1")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                // then
                .expectStatus().isBadRequest
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.timestamp").isNumber
                .jsonPath("$.path").isEqualTo("/routes/1")
    }

    @Test
    fun testSaveRoute() {
        // given
        val routeId = UUID.randomUUID()
        val cragId = UUID.randomUUID()
        val sectorId = UUID.randomUUID()
        Mockito.`when`(routesService.saveRoute(any()))
                .then { invocation ->
                    val routeDTO = invocation.getArgument<RouteDTO>(0)
                    Mono.just(Route(routeId,
                            routeDTO.name ?: "",
                            routeDTO.type ?: RouteType.UNKNOWN,
                            routeDTO.grade ?: RouteGrade.UNKNOWN,
                            routeDTO.country ?: "",
                            routeDTO.region ?: "",
                            routeDTO.cragId ?: UUID.randomUUID(),
                            routeDTO.cragName ?: "",
                            routeDTO.sectorId ?: UUID.randomUUID(),
                            routeDTO.sectorName ?: ""))
                }

        // when
        val routeDtoAsJson = """
            {
                "name": "route",
                "type": "Sport",
                "grade": "4a",
                "country": "Italy",
                "region": "Cala Gonone",
                "cragId": "${cragId.toString()}",
                "cragName": "Millennium",
                "sectorId": "${sectorId.toString()}",
                "sectorName": "Millennium"
            }
        """.trimIndent()

        webTestClient.post()
                .uri("/routes")
                .body(Mono.just(routeDtoAsJson), String::class.java)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                // then
                .expectStatus().isCreated
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isEqualTo(routeId.toString())
                .jsonPath("$.name").isEqualTo("route")
                .jsonPath("$.type").isEqualTo("Sport")
                .jsonPath("$.grade").isEqualTo("4a")
                .jsonPath("$.country").isEqualTo("Italy")
                .jsonPath("$.region").isEqualTo("Cala Gonone")
                .jsonPath("$.cragId").isEqualTo(cragId.toString())
                .jsonPath("$.cragName").isEqualTo("Millennium")
                .jsonPath("$.sectorId").isEqualTo(sectorId.toString())
                .jsonPath("$.sectorName").isEqualTo("Millennium")
    }

    @Test
    fun testSaveRouteWithInvalidCragId() {
        TODO("not implemented")
    }

    @Test
    fun testSaveRouteWithInvalidSectorId() {
        TODO("not implemented")
    }

    @Test
    fun testDeleteRoute() {
        TODO("not implemented")
    }

    @Test
    fun testDeleteRouteWithNonexistentId() {
        TODO("not implemented")
    }

    @Test
    fun testDeleteRouteWithInvalidId() {
        TODO("not implemented")
    }

    @Test
    fun testUpdateRoute() {
        TODO("not implemented")
    }

    @Test
    fun testUpdateRouteWithNonexistentId() {
        TODO("not implemented")
    }

    @Test
    fun testUpdateRouteWithInvalidId() {
        TODO("not implemented")
    }

    @Test
    fun testUpdateRouteWithInvalidCragId() {
        TODO("not implemented")
    }

    @Test
    fun testUpdateRouteWithInvalidSectorId() {
        TODO("not implemented")
    }

    private fun createRoute(routeName: String): Route {
        return createRoute(UUID.randomUUID(), routeName)
    }

    private fun createRoute(id: UUID, routeName: String): Route {
        return Route(id,
                routeName,
                RouteType.SPORT,
                RouteGrade.GRADE_4a,
                "Italy",
                "Cala Gonone",
                UUID.randomUUID(),
                "Millennium",
                UUID.randomUUID(),
                "Millennium")
    }
}