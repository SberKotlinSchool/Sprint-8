package ru.sberschool.hystrix

import com.google.gson.annotations.SerializedName


data class Pokemon(
    val id: Long,
    @SerializedName("is_battle_only")
    val isBattleOnly: Boolean,
    @SerializedName("is_default")
    val isDefault: Boolean,
    @SerializedName("is_mega")
    val isMega: Boolean,
    val name: String,
){
    override fun toString(): String {
        return "Pokemon(id=$id, isBattleOnly=$isBattleOnly, isDefault=$isDefault, isMega=$isMega, name='$name')"
    }
}