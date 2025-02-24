package com.fekent.skyjo.engine

import kotlinx.serialization.Serializable


@Serializable
sealed interface GameAction{

    val id: String
    val timestamp: String
    val playerId: PlayerId
}