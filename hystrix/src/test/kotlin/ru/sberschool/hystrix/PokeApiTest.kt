package ru.sberschool.hystrix

import feign.Request
import feign.httpclient.ApacheHttpClient
import feign.hystrix.HystrixFeign
import feign.jackson.JacksonDecoder
import feign.jackson.JacksonEncoder
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit
import ru.sberschool.hystrix.domain.Item
import kotlin.test.assertEquals

class PokeApiTest {
  @Test
  fun `getItem() should return real data`() {
    val pokeClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(10, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true))
        .target(PokeApiClient::class.java, "https://pokeapi.co/api/v2", FallbackPokeApi())
    val item = pokeClient.getItem(12)
    assertEquals("premier-ball", item.name)
  }

  @Test
  fun `getItem should return predefined data`() {
    val mockClient = HystrixFeign
        .builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .encoder(JacksonEncoder())
        .options(Request.Options(1, TimeUnit.MILLISECONDS, 1, TimeUnit.MILLISECONDS, true))
        .target(PokeApiClient::class.java, "https://pokeapi.co/api/v2", FallbackPokeApi())

    assertEquals(Item(id = 12, name = "abc", cost = 10), mockClient.getItem(12))
  }
}