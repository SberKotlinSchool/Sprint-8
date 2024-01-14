package ru.sberschool.hystrix

import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse

class PokemonApiTest {

    private val client: PokemonApi = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(PokemonApi::class.java, "https://pokeapi.co/api/v2", FallbackPokemonApi())

    private val fallBackClient: PokemonApi = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(PokemonApi::class.java, "http://127.0.0.1:18080", FallbackPokemonApi())

    private lateinit var mockServer: ClientAndServer

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
    fun `getPokemon(id) should return pokemon bulbasaur`() {
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
        assertEquals("bulbasaur", client.getPokemon(1).name)
    }

    @Test
    fun `getPokemon(id) with fallback should return pokemon clefairy`() {
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
        assertEquals("clefairy", fallBackClient.getPokemon(1).name)
    }
}