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
import kotlin.test.assertEquals

class PokemonApiTest {

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
    fun `getPokemonByYellowColor() should return predefined data`() {
        // given
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                // задаем матчер для нашего запроса
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/pokemon-color/yellow")
            )
            .respond(
                // наш запрос попадает на таймаут
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 30) //
            )

        val client = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(SlowlyApi::class.java, "http://127.0.0.1:18080", FallbackSlowlyApi())
        // expect
        assertEquals(Pokemon(104L, "UniversalYellowPokemon" ), client.getPokemonByYellowColor())
    }

    @Test
    fun `getPokemonByYellowColor() should return real data`() {
        // given
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/pokemon-color/yellow")
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(HttpStatusCode.OK_200.code())
                    .withBody(
                        """{
                             {"id":10,"name":"yellow","names":[{"language":{"name":"ja-Hrkt","url":"https://pokeapi.co/api/v2/language/1/"},"name":"きいろ"},{"language":{"name":"ko","url":"https://pokeapi.co/api/v2/language/3/"},"name":"노랑"},{"language":{"name":"zh-Hant","url":"https://pokeapi.co/api/v2/language/4/"},"name":"黃色"},{"language":{"name":"fr","url":"https://pokeapi.co/api/v2/language/5/"},"name":"Jaune"},{"language":{"name":"de","url":"https://pokeapi.co/api/v2/language/6/"},"name":"Gelb"},{"language":{"name":"es","url":"https://pokeapi.co/api/v2/language/7/"},"name":"Amarillo"},{"language":{"name":"it","url":"https://pokeapi.co/api/v2/language/8/"},"name":"Giallo"},{"language":{"name":"en","url":"https://pokeapi.co/api/v2/language/9/"},"name":"Yellow"},{"language":{"name":"ja","url":"https://pokeapi.co/api/v2/language/11/"},"name":"黄色"},{"language":{"name":"zh-Hans","url":"https://pokeapi.co/api/v2/language/12/"},"name":"黄色"}],"pokemon_species":
                             {"name":"gholdengo","url":"https://pokeapi.co/api/v2/pokemon-species/1000/"}]}
                         }""".trimIndent()
                    )
            )

        val client = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(SlowlyApi::class.java, "http://127.0.0.1:18080", FallbackSlowlyApi())

        val pokemonByYellowColor = client.getPokemonByYellowColor()
        // expect
        assertEquals(Pokemon(104L, "UniversalYellowPokemon" ), client.getPokemonByYellowColor())
        assertEquals(104, pokemonByYellowColor.id)
    }
}
