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
import org.mockserver.model.HttpError
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class SlowlyApiTest {
    val client = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        // для удобства тестирования задаем таймауты на 1 секунду
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(SlowlyApi::class.java, "https://pokeapi.co/api/v2", FallbackSlowlyApi())
    val clientFB = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        // для удобства тестирования задаем таймауты на 1 секунду
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(SlowlyApi::class.java, "https://pokeapi.co/ap/v2", FallbackSlowlyApi())
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
    fun `getSomething(1) should return hp`() {
        assertEquals("hp", client.getSomething(1).name)
    }

    @Test
    fun `getSomething(1) should return predefined data`() {
        // given
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                // задаем матчер для нашего запроса
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/stat/1")
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS,30)
            )
        // expect
        assertEquals("predefined data", clientFB.getSomething(1).name)
    }
}
