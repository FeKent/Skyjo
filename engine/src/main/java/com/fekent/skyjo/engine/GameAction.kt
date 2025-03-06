package com.fekent.skyjo.engine

import jdk.jfr.Timestamp
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


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
        val playerOrder: List<PlayerId>,
        val allBoards: Map<PlayerId, Board>
    ): GameAction

    @Serializable
    @SerialName("InitialFlipCard")
    data class InitialFlipCard(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val deck: Deck,
        val card: Card,
        val initialScores: Map<PlayerId, Int>
    ) : GameAction

    @Serializable
    @SerialName("FlipCard")
    data class FlipCard(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val deck: Deck,
        val card: Card,
    ) : GameAction

    @Serializable
    @SerialName("StartRound")
    data class StartRound(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val playOrder: Map<PlayerId, Int>,
        val previousRoundEnder: PlayerId,
    ) : GameAction

    @Serializable
    @SerialName("DrawCard")
    data class DrawCard(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val discardPile: Boolean,
        val drawPile: Boolean,
        val drawnCard: Card
    ) : GameAction

    @Serializable
    @SerialName("DiscardCard")
    data class DiscardCard(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val currentCard: Card,
        val discardedCards: List<Card> //unsure about this one
    ) : GameAction

    @Serializable
    @SerialName("KeepCard")
    data class KeepCard(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val currentCard: Card,
        val pendingDeck: List<Card>
    ) : GameAction

    @Serializable
    @SerialName("DiscardSkyjo")
    data class DiscardSkyjo(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val skyjo: Boolean,
        val discardedCards: List<Card>
    ) : GameAction

    @Serializable
    @SerialName("CheckScore")
    data class CheckScore(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
        val currentScores: Map<PlayerId, Int>
    ) : GameAction

    @Serializable
    @SerialName("ViewedResult")
    data class ViewedResult(
        override val id: String,
        override val timestamp: String,
        override val playerId: PlayerId,
    ): GameAction
}

object GameActionUtilities {
    @Suppress("NewApi")
    private val timestampFormatter : DateTimeFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-d'T'HH:mm:ss'Z'")

    @OptIn(ExperimentalUuidApi::class)
    fun generateId(): String {
        return Uuid.random().toHexString()
    }

    @Suppress("NewApi")
    fun generateTimestamp(): String {
        return timestampFormatter.format(LocalDateTime.now())
    }
}