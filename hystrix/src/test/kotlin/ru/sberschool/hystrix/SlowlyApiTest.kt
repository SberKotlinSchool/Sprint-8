package ru.sberschool.hystrix

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

const val PORT = 18080

class SlowlyApiTest {

    lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setup() {
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

        val page = hystrixClient("http://127.0.0.1:${PORT}").getPokemonPage()
        // expect
        assertEquals(3, page.count)
        assertEquals(NEXT_URL, page.next)
        assertNull(page.previous)
        assertEquals(3, page.results.size)
        assertThat(page.results.map { it.name }).containsExactlyInAnyOrder("pokemon1","pokemon2","pokemon3")
    }


    @Test
    fun `getPokemonPage() should return real pokemon list`() {
        val page = hystrixClient("https://pokeapi.co/api/v2").getPokemonPage()
        assertEquals(1302, page.count)
        assertEquals(NEXT_URL, page.next)
        assertNull(page.previous)
        assertThat(page.results.map { it.name }).containsExactlyInAnyOrder("bulbasaur", "ivysaur", "venusaur")
    }

    private fun hystrixClient(url: String) : SlowlyApi{
        val objectMapper = ObjectMapper()
                .registerModule(KotlinModule())
        return HystrixFeign.builder()
                .client(ApacheHttpClient())
                .decoder(JacksonDecoder(objectMapper))
                // для удобства тестирования задаем таймауты на 1 секунду
                .options(Request.Options(2, TimeUnit.SECONDS, 2, TimeUnit.SECONDS, true))
                .target(SlowlyApi::class.java, url, FallbackSlowlyApi())
    }
}
