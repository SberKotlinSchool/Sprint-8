package ru.sberschool.hystrix

import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import io.netty.util.internal.logging.InternalLoggerFactory
import io.netty.util.internal.logging.Slf4JLoggerFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals


private const val MOC_HOST = "127.0.0.1"
private const val MOC_PORT = 18080

class SlowlyApiTest {
    private lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setup() {
        InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE)
        // запускаем мок сервер для тестирования клиента
        mockServer = ClientAndServer.startClientAndServer(MOC_PORT)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    @Test
    fun `getSomething() should return predefined data`() {
        // given
        MockServerClient(MOC_HOST, MOC_PORT)
            .`when`(
                // задаем матчер для нашего запроса
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/pokemon/1")
            )
            .respond(
                // наш запрос попадает на таймаут
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 30) //
            )

        val client = getClient("http://${MOC_HOST}:${MOC_PORT}")
        val expected = SimpleResponse()
        // when
        val actual = client.getSomething(1)
        // then
        assertEquals(expected, actual)
    }


    @Test
    fun `getSomething() should return data from external`() {
        // given
        val client = getClient("https://pokeapi.co/api/v2/")
        val expected =
            SimpleResponse(
                id = 1,
                name = "bulbasaur",
                base_experience = 64,
                height = 7,
                order = 1,
                weight = 69
            )
        // when
        val actual = client.getSomething(1)
        // then
        assertEquals(expected, actual)
    }

    private fun getClient(url: String) = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        // для удобства тестирования задаем таймауты на 1 секунду
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(SlowlyApi::class.java, url, FallbackSlowlyApi())
}
