package ru.sberschool.hystrix

import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Location(
    @JsonProperty("id")
    val id: Long? = null,
    @JsonProperty("name")
    val name: String? = null,
    @JsonProperty("game_index")
    val gameIndex: Int? = null,
    @JsonProperty("encounter_method_rates")
    val encounterMethodRates: List<EncounterMethodRate>? = null,
    @JsonProperty("location")
    val location: NamedAPIResource<Location>? = null,
    @JsonProperty("names")
    val names: List<Name>? = null,
    @JsonProperty("pokemon_encounters")
    val pokemonEncounters: List<PokemonEncounter>? = null
)
@JsonIgnoreProperties(ignoreUnknown = true)
data class EncounterMethodRate(
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class NamedAPIResource<T>(
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Name(
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PokemonEncounter(
)
