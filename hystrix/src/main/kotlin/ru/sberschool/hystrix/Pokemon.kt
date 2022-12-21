package ru.sberschool.hystrix

data class Pokemon(
    val id: Long,
    val is_battle_only: Boolean,
    val is_default: Boolean,
    val is_mega: Boolean,
    val name: String,
){
    override fun toString(): String {
        return "Pokemon(id=$id, is_battle_only=$is_battle_only, is_default=$is_default, is_mega=$is_mega, name='$name')"
    }
}