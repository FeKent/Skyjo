package com.fekent.engine

import com.fekent.skyjo.engine.Board
import com.fekent.skyjo.engine.Rules
import com.fekent.skyjo.engine.Rules.fullDeck
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DeckTest {

    @Test
    fun fullDeckWith150Cards() {
        fullDeck

        assertEquals(150, fullDeck.cards.size)

        val shuffledDeck = fullDeck.cards.shuffled()

        val randomCard = shuffledDeck[7]
        println("Shuffled card value: ${randomCard.value}")

        val randomBoard = shuffledDeck.take(12)
        println("Shuffled Board:  ${randomBoard.joinToString { it.value.toString() }}")

    }

    @Test
    fun generatePlayerBoardFromDeck() {
        val emptyBoard = Board(emptyList())
        val playerBoard = emptyBoard.startingBoard(fullDeck.cards.shuffled())

        println("Starting Board Cards: ${playerBoard.cards.joinToString { it.value.toString() }}")

        assertEquals(Rules.initialBoardSize, playerBoard.cards.size)
    }
}