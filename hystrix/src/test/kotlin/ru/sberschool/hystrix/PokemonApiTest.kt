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
import ru.sberschool.entity.PokemonRs
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class PokemonApiTest {

    lateinit var mockServer: ClientAndServer
    private val id = 10L

    @BeforeEach
    fun setup() {
        mockServer = ClientAndServer.startClientAndServer(18080)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    @Test
    fun `getPokemon(id) should return data from remote resource`() {
        val client = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(PokemonApi::class.java, "https://pokeapi.co/api/v2/", FallbackPokemonApi())
        val expectedPokemon =
            PokemonRs(id = 10, name = "caterpie", order = 14, baseExperience = 39, height = 3, weight = 29)
        val actualPokemon = client.getPokemon(expectedPokemon.id!!)
        assertEquals(expectedPokemon, actualPokemon)
    }

    @Test
    fun `getPokemon(id) should return predefined fallback data`() {
        val client = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(PokemonApi::class.java, "http://127.0.0.1:18080", FallbackPokemonApi())
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/pokemon/$id")
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 10) //
            )
        assertEquals(PokemonRs(-1, "UNDEFINED"), client.getPokemon(id))
    }
}
