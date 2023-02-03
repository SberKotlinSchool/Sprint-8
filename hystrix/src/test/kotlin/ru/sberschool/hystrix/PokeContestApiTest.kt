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
import kotlin.test.assertNotEquals

private const val TIMEOUT = 10L

private const val MOCK_SERVER_IP = "http://127.0.0.1"
private const val MOCK_SERVER_PORT = 18080

private const val REAL_SERVER_HOST = "https://pokeapi.co/api/v2"

class SlowlyApiTest {
    lateinit var mockServer: ClientAndServer

    private val brokenClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(TIMEOUT, TimeUnit.SECONDS, TIMEOUT, TimeUnit.SECONDS, true))
        .target(PokeContestApi::class.java, "$MOCK_SERVER_IP:$MOCK_SERVER_PORT", FallbackSlowlyApi())

    private val workingClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(TIMEOUT, TimeUnit.SECONDS, TIMEOUT, TimeUnit.SECONDS, true))
        .target(PokeContestApi::class.java, REAL_SERVER_HOST, FallbackSlowlyApi())

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
    fun `getCoolContestType() should return predefined data`() {
        // given
        MockServerClient(MOCK_SERVER_IP, MOCK_SERVER_PORT)
            .`when`(
                // задаем матчер для нашего запроса
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/contest-type/cool")
            )
            .respond(
                // наш запрос попадает на таймаут
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 15)
            )
        // expect
        assertEquals(DEFAULT, brokenClient.getCoolContestType())
    }

    @Test
    fun `getCoolContestType() should return proper data`() {
        val contestType = workingClient.getCoolContestType()
        assertNotEquals(DEFAULT, contestType)
    }
}
