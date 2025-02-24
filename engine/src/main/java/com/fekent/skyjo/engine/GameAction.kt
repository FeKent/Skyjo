package com.fekent.skyjo.engine

import jdk.jfr.Timestamp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed interface GameAction {

    val id: String
    val timestamp: String
    val playerId: PlayerId

    @Serializable
    @SerialName("JoinedLobby")
    data class JoinedLobby(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val playerName: String,
    ) : GameAction

    @Serializable
    @SerialName("LeftLobby")
    data class LeftLobby(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
    ) : GameAction

    @Serializable
    @SerialName("StartGame")
    data class StartGame(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val players: List<PlayerId>,
        val allDecks: Map<PlayerId, Deck>
    ): GameAction

}