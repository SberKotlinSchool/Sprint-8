package ru.sberschool.hystrix

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
import org.mockserver.model.HttpStatusCode
import java.util.concurrent.TimeUnit
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull
import org.mockserver.model.Parameter

internal class PokeApiTest {
    private val client = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        // для удобства тестирования задаем таймауты на 1 секунду
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(PokeApi::class.java, "http://127.0.0.1:18080", PokeApiFallback())

    lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setup() {
        // запускаем мок сервер для тестирования клиента
        mockServer = ClientAndServer.startClientAndServer(18080)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    @Test
    fun `getBerries return real data`() {
        // given
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/api/v2/berry/")
                    .withQueryStringParameters(
                        listOf(
                            Parameter("offset", "0"),
                            Parameter("limit", "1")
                        )
                    )
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(HttpStatusCode.OK_200.code())
                    .withBody(
                        """{
                            "count": 64,
                            "next": "https://pokeapi.co/api/v2/berry/?offset=1&limit=1",
                            "previous": null,
                            "results": [
                                {
                                    "name": "cheri",
                                    "url": "https://pokeapi.co/api/v2/berry/1/"
                                }
                            ]
                        }""".trimIndent()
                    )
            )

        val requestParams = BerryRequestParams(0, 1)
        val response = client.getBerries(requestParams)

        // expect
        assertEquals(64, response.count)
        assertEquals("https://pokeapi.co/api/v2/berry/?offset=1&limit=1", response.next)
        assertNull(response.previous)
        assertContentEquals(
            listOf(BerryResult("cheri", "https://pokeapi.co/api/v2/berry/1/")),
            response.results
        )
    }

    @Test
    fun `getBerries return predefined data`() {
        // given
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/api/v2/berry/")
                    .withQueryStringParameters(
                        listOf(
                            Parameter("offset", "0"),
                            Parameter("limit", "1")
                        )
                    )
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 30)
            )

        val requestParams = BerryRequestParams(0, 1)
        val response = client.getBerries(requestParams)

        // expect
        assertEquals(10, response.count)
        assertEquals("nextPage", response.next)
        assertNull(response.previous)
        assertContentEquals(
            listOf(BerryResult("someBerry", "https://pokeapi.site/urlToBerry")),
            response.results
        )
    }
}
