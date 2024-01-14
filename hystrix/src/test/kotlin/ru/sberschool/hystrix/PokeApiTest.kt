package ru.sberschool.hystrix

import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import org.junit.jupiter.api.Test
import ru.sberschool.client.FallbackPokeApi
import ru.sberschool.client.PokeApiClient
import ru.sberschool.client.domain.Berry
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class PokeApiTest {
    @Test
    fun `getBerry() should return real data`() {
        val pokeClient = HystrixFeign.builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            // для удобства тестирования задаем таймауты на 1 секунду
            .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
            .target(PokeApiClient::class.java, "https://pokeapi.co/api/v2", FallbackPokeApi())
        val berry = pokeClient.getBerry(1)
        assertEquals("cheri", berry.name)
    }

    @Test
    fun `getBerry should return predefined data`() {
        val mockClient = HystrixFeign
            .builder()
            .client(ApacheHttpClient())
            .decoder(JacksonDecoder())
            .encoder(JacksonEncoder())
            .options(Request.Options(1, TimeUnit.MILLISECONDS, 1, TimeUnit.MILLISECONDS, true))
            .target(PokeApiClient::class.java, "https://pokeapi.co/12", FallbackPokeApi())

        assertEquals(Berry(20, "raspberries", 1, 1, 1,
            1, 1, 1), mockClient.getBerry(1))
    }
}