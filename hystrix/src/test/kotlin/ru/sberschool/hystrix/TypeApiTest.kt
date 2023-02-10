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

class TypeApiTest {

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

    val client = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(30, TimeUnit.SECONDS, 30, TimeUnit.SECONDS, true))
        .target(TypeApi::class.java, "https://pokeapi.co/api/v2/", FallbackTypeApi())

    val mockClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(30, TimeUnit.SECONDS, 30, TimeUnit.SECONDS, true))
        .target(TypeApi::class.java, "http://127.0.0.1:18080", FallbackTypeApi())


    @Test
    fun `getType should return real object`() {
        val type = client.getTypeById(1)
        assertEquals(1, type.id)
        assertEquals("normal", type.name)
        assertEquals(154, type.pokemons.size)
        assertEquals(200, type.moves.size)
    }

    @Test
    fun `getType should return fallback data`() {
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
        assertEquals("EmptyType", mockClient.getTypeById(11).name)
    }
}
