package hystrix
import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import ru.sberschool.hystrix.api.FallBackPokeFeignApi
import ru.sberschool.hystrix.api.PokeFeignApi
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals


class PokeFeignApiTest {

    lateinit var mockServer: ClientAndServer


    @BeforeEach
    fun setup() {
        mockServer = ClientAndServer.startClientAndServer(18080)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    val  client = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(30, TimeUnit.SECONDS, 30, TimeUnit.SECONDS, true))
        .target(PokeFeignApi::class.java, "https://pokeapi.co/api/v2",FallBackPokeFeignApi())

    val mockClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(30, TimeUnit.SECONDS, 30, TimeUnit.SECONDS, true))
        .target(PokeFeignApi::class.java, "http://127.0.0.1:18080",FallBackPokeFeignApi())

    @Test
    fun `get Item shoud return real Api`() {
        val pokeItem = client.getItem(1)
        assertEquals(1, pokeItem.id)
        assertEquals(0, pokeItem.cost)
        assertEquals("master-ball", pokeItem.name)
    }

    @Test
    fun `getItem should Fallback`() {
        // given
        MockServerClient("127.0.0.1:18080", 18080)
            .`when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/api/v2/")
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 30) //
            )
        // expect
        assertEquals("Empty Item", mockClient.getItem(1).name)
    }

}