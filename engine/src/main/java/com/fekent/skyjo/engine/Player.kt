package com.fekent.skyjo.engine

import kotlinx.serialization.Serializable

@Serializable
class Player(
    val id: PlayerId,
    val name: String
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Player
        return (id == other.id)
    }

    override fun hashCode(): Int = id.hashCode()

}
