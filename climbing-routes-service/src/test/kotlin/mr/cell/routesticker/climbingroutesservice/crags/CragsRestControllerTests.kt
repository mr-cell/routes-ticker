package mr.cell.routesticker.climbingroutesservice.crags

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@WebFluxTest(CragsRestController::class)
class CragsRestControllerTests {

    @MockBean
    private lateinit var cragsService: CragsService

    @Test
    fun test() {

    }
}