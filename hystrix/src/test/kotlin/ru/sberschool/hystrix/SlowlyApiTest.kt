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
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import kotlin.test.assertNull

const val PORT = 18080

class SlowlyApiTest {

    lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setup() {
        // запускаем мок сервер для тестирования клиента
        mockServer = ClientAndServer.startClientAndServer(PORT)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    @Test
    fun `getPokemonPage() should return fallback data`() {
        // given
        MockServerClient("127.0.0.1", PORT)
            .`when`(
                // задаем матчер для нашего запроса
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/pokemon")
            )
            .respond(
                // наш запрос попадает на таймаут
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 10)
            )

        val client = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(SlowlyApi::class.java, "http://127.0.0.1:${PORT}", FallbackSlowlyApi())

        // expect
        assertEquals(3, client.getPokemonPage().count)
        assertEquals(NEXT_URL, client.getPokemonPage().next)
        assertNull(client.getPokemonPage().previous)
        assertEquals(3, client.getPokemonPage().results.size)
        assertEquals("ivysaur", client.getPokemonPage().results[1].name)
    }

    @Test
    fun `getPokemonPage() should return real pokemon list`() {
        val pokeClient = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(SlowlyApi::class.java, "https://pokeapi.co/api/v2", FallbackSlowlyApi())

        assertEquals(1279, pokeClient.getPokemonPage().count)
        assertEquals(NEXT_URL, pokeClient.getPokemonPage().next)
        assertNull(pokeClient.getPokemonPage().previous)
    }
}
