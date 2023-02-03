package ru.sberschool.hystrix

import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockserver.client.server.MockServerClient
import org.mockserver.integration.ClientAndServer
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import java.util.concurrent.TimeUnit

class BerryApiTest {

    lateinit var mockServer: ClientAndServer
    private val timeOut : Long = 20

    @BeforeEach
    fun setup() {
        mockServer = ClientAndServer.startClientAndServer(18080)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    val berryClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(timeOut, TimeUnit.SECONDS, timeOut, TimeUnit.SECONDS, true))
        .target(BerryApi::class.java, "https://pokeapi.co/api/v2",FallbackBerryApi())

    val fallbackClient: BerryApi = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(timeOut, TimeUnit.SECONDS, timeOut, TimeUnit.SECONDS, true))
        .target(BerryApi::class.java, "http://127.0.0.1:18080",FallbackBerryApi())

    @Test
    fun `return data from api test`() {
        val berry = berryClient.getBerry (20)
        assertEquals(20, berry.id)
        assertEquals("pinap", berry.name)
        assertEquals(70, berry.power)
        assertEquals(20, berry.smoothness)
    }

    @Test
    fun `fallback test`() {
        MockServerClient("127.0.0.1:18080", 18080)
            .`when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/api/v2/")
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 30) //
            )
        assertEquals("No berry", fallbackClient.getBerry(1).name)
    }
}