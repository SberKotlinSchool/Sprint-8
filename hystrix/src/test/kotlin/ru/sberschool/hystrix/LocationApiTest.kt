class LocationApiTest {
    val client = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(1, TimeUnit.SECONDS, 1, TimeUnit.SECONDS, true))
        .target(LocationsApi::class.java, "https://pokeapi.co", FallbackLocationsApi())

    val fallbackClient = HystrixFeign.builder()
        .client(ApacheHttpClient())
        .decoder(JacksonDecoder())
        .options(Request.Options(10, TimeUnit.SECONDS, 10, TimeUnit.SECONDS, true))
        .target(LocationsApi::class.java, "http://127.0.0.1:18080", FallbackLocationsApi())
    lateinit var mockServer: ClientAndServer

    @BeforeEach
    fun setup() {
        mockServer = ClientAndServer.startClientAndServer(18080)
    }

    @AfterEach
    fun shutdown() {
        mockServer.stop()
    }

    @Test
    fun fallbackTest() {
        MockServerClient("127.0.0.1", 18080)
            .`when`(
                HttpRequest.request()
                    .withMethod("GET")
                    .withPath("/api/v2/location/1")
            )
            .respond(
                HttpResponse.response()
                    .withStatusCode(400)
                    .withDelay(TimeUnit.SECONDS, 20) //
            )
        assertEquals(Location(), fallbackClient.getLocation(1))
    }

    @Test
    fun realTest() {
        val expectedLocation = Location(
            1,
            "canalave-city",
            NamedAPIResource(4, "sinnoh"),
            listOf(Name("Canalave City", NamedAPIResource(9, "en"))),
            listOf(
                GenerationGameIndex(
                    7,
                    NamedAPIResource("generation-iv", "https://pokeapi.co/api/v2/generation/4/")
                )
            ),
            listOf(NamedAPIResource("canalave-city-area", "https://pokeapi.co/api/v2/location-area/1/"))
        )
        assertEquals(expectedLocation, client.getLocation(1))
    }
}