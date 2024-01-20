package ru.sberschool.hystrix

import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import io.netty.util.internal.logging.InternalLoggerFactory
import io.netty.util.internal.logging.Slf4JLoggerFactory
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse

class SlowlyApiTest {
    private lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setup() {
        InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE)
        // запускаем мок сервер для тестирования клиента
        mockServer = ClientAndServer.startClientAndServer(18080)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    @Test
    fun `getSomething() should return predefined data`() {
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/pokemon/1")
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 30) //
            )

        val client = getClient("http://127.0.0.1:18080")
        val expected = SimpleResponse()
        val actual = client.getSomething(1)
        assertEquals(expected, actual)
    }



    @Test
    fun `getSomething() should return data from external`() {
        val client = getClient("https://pokeapi.co/api/v2/")
        val expected =
            SimpleResponse(
                id = 35,
                name = "clefairy",
                base_experience = 113,
                height = 6,
                order = 64,
                weight = 75
            )
        val actual = client.getSomething(35)
        assertEquals(expected, actual)
    }

    private fun getClient(url: String) = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .target(SlowlyApi::class.java, url, FallbackSlowlyApi())
}