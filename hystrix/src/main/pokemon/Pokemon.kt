package pokemon

import com.fasterxml.jackson.annotation.JsonProperty

data class Pokemon(
    @JsonProperty("id")
    val id: Int?,

    @JsonProperty("name")
    val name: String?,

    @JsonProperty("base_experience")
    val baseExperience: Int? = null,

    @JsonProperty("height")
    val height: Int? = null,

    @JsonProperty("is_default")
    val isDefault: Boolean? = null,

    @JsonProperty("order")
    val order: Int? = null,

    @JsonProperty("weight")
    val weight: Int? = null
)