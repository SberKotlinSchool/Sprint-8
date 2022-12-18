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
import kotlin.test.assertTrue

class SlowlyApiTest {
    val localHost = "127.0.0.1"
    val localPort = 18080

    val client = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        // для удобства тестирования задаем таймауты на 1 секунду
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(SlowlyApi::class.java, "https://www.cbr-xml-daily.ru", FallbackSlowlyApi())

    lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setup() {
        // запускаем мок сервер для тестирования клиента
        mockServer = ClientAndServer.startClientAndServer(localPort)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    @Test
    fun `getSomething() should return predefined data`() {
        val data = client.getCourses() as Map<*, *>
        assertTrue { data.containsKey("Valute") }
    }

    @Test
    fun testFallbackCall() {
        MockServerClient(localHost, localPort)
            .`when`(
                // задаем матчер для нашего запроса
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/daily_json.js")
            )
            .respond(
                // наш запрос попадает на таймаут
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 1) //
            )

        val fallbackClient =  HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(SlowlyApi::class.java, "http://$localHost:$localPort", FallbackSlowlyApi())

        val data = fallbackClient.getCourses() as Map<*, *>
        assertTrue { data.containsKey("Error") }
    }
}
