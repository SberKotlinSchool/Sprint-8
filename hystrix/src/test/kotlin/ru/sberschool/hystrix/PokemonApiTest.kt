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
import ru.sberschool.client.Pokemon
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class PokemonApiTest {

    lateinit var mockServer: ClientAndServer
    private val id = 2L

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
    fun `getPokemon(id) should return predefined data`() {
        val client = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(PokemonApi::class.java, "http://127.0.0.1:18080", FallbackPokemonApi())
        // given
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                // задаем матчер для нашего запроса
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/pokemon/$id")
            )
            .respond(
                // наш запрос попадает на таймаут
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 30) //
            )
        // expect
        assertEquals(Pokemon(0, "NOT_FOUND"), client.getPokemon(id))
    }

    @Test
    fun `getPokemon(id)`() {
        val client = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(PokemonApi::class.java, "https://pokeapi.co/api/v2/", FallbackPokemonApi())
        val expectedPokemon =
            Pokemon(id = 2, name = "bulbasaur", baseExperience = 77, height = 7, weight = 777)
        val actualPokemon = client.getPokemon(expectedPokemon.id!!)
        assertEquals(expectedPokemon, actualPokemon)
    }

}
