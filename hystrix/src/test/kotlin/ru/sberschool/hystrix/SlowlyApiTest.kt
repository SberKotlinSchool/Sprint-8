package ru.sberschool.hystrix

import feign.Request
import feign.gson.GsonDecoder
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

class вSlowlyApiTest {
    val fallbackPokemon = Pokemon(1L, false, false, false, "ServerFallback")
    val bulbasaurPokemon = Pokemon(1L, false, true, false, "bulbasaur")

    val clientFallback = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        // для удобства тестирования задаем таймауты на 1 секунду
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(SlowlyApi::class.java, "http://127.0.0.1:18080", FallbackSlowlyApi())

    val clientReal = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(GsonDecoder())
        .target(SlowlyApi::class.java, "https://pokeapi.co/api/v2/pokemon/")


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
    fun `findById() should return circuit breaker data`() {
        // given
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                // задаем матчер для нашего запроса
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/")
            )
            .respond(
                // наш запрос попадает на таймаут
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 30) //
            )
        // expect
        assertEquals(fallbackPokemon, clientFallback.findById(1L))
    }

    @Test
    fun `findById() should return bulbasaur data`() {
        assertEquals(bulbasaurPokemon, clientReal.findById(1L))
    }
}
