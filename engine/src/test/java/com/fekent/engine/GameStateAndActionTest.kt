package com.fekent.engine

import com.fekent.skyjo.engine.GameAction
import com.fekent.skyjo.engine.GameState
import com.fekent.skyjo.engine.Player
import com.fekent.skyjo.engine.PlayerId
import com.fekent.skyjo.engine.Rules
import com.fekent.skyjo.engine.reduce
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class GameStateAndActionTest {

    @Test
    fun `when game starts with 4 players, each player has a board with cards`() {
        // Create 4 players
        val players = listOf(
            Player(PlayerId("1"), "August"),
            Player(PlayerId("2"), "Gabi"),
            Player(PlayerId("3"), "Charlie"),
            Player(PlayerId("4"), "Snippy")
        )

        val playerIds = players.map { it.id }

        // Get initial decks
        val initialDeck = Rules.fullDeck
        val initialDeckSize = initialDeck.cards.size

        // Deal boards to the players
        val initialBoards = Rules.deal(playerIds)

        val startGameAction = GameAction.StartGame(
            players = playerIds,
            allBoards = initialBoards, // No need to manually assign cards
            id = "TestString",
            timestamp = "TestString",
            playerId = PlayerId("1")
        )

        // Start game from NotStarted state
        val initialState = GameState.NotStarted(players)
        val newState = initialState.reduce(startGameAction)

        // Ensure state transitioned to AwaitingRoundStart
        assertTrue(newState is GameState.StartedGameState.LiveGameState.AwaitingRoundStart)

        val awaitingRoundStart = newState as GameState.StartedGameState.LiveGameState.AwaitingRoundStart

        // Ensure all players have a board with cards
        assertEquals(players.size, awaitingRoundStart.allBoards.size)
        awaitingRoundStart.allBoards.forEach { (playerId, board) ->
            assertTrue(board.cards.isNotEmpty()) // Ensure each board has cards
            println("${players.find { it.id == playerId }?.name}'s board: ${board.cards.joinToString { it.value.toString() }}")
        }

        // Print the initial deck size and remaining deck size for debugging
        println("Initial deck size: $initialDeckSize")
        println("Remaining deck size after dealing boards: ${awaitingRoundStart.deck.cards.size}")

        // Calculate the expected remaining deck size
        val expectedRemainingDeckSize = initialDeckSize - (players.size * Rules.initialBoardSize)

        // Ensure the remaining deck size is correct
        val remainingDeckSize = awaitingRoundStart.deck.cards.size
        assertEquals(expectedRemainingDeckSize, remainingDeckSize)

        // Print the remaining deck size for debugging
        println("Remaining deck size: $remainingDeckSize")
        println("Deck Check: ${initialDeck.cards.joinToString { it.value.toString() }}")
    }
}