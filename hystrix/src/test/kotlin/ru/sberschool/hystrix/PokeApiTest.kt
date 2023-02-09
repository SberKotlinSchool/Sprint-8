package ru.sberschool.hystrix

import feign.Feign
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
import kotlin.test.assertNotNull

class PokeApiTest {

    lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setup() {
    }

    @AfterEach
    fun shutdown() {
    }

    @Test
    fun `getSomething() should return normal response`(){
        val realClient = Feign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            .target(PokeApi::class.java, "https://xn--80aqu.xn--90adear.xn--p1ai")

        assertEquals("200", realClient.getSomething(1108731).status)
        assertEquals("", realClient.getSomething(1108731).message)
        assertNotNull(realClient.getSomething(1108731).timestamp)
    }

    @Test
    fun `getSomething() should return predefined data`() {
        // запускаем мок сервер для тестирования клиента
        mockServer = ClientAndServer.startClientAndServer(18080)
        val client = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(PokeApi::class.java, "http://127.0.0.1:18080", FallbackPokeApi())

        // given
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                // задаем матчер для нашего запроса
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/v2/divisions/{id}")
                    .withQueryStringParameter("5")
            )
            .respond(
                // наш запрос попадает на таймаут
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 30) //
            )
        // expect
        assertEquals("fallback data status", client.getSomething(5).status)
        assertEquals("fallback data message", client.getSomething(5).message)
        mockServer.stop()
    }
}