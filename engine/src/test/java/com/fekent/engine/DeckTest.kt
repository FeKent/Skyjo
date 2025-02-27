package com.fekent.engine

import com.fekent.skyjo.engine.Rules.fullDeck
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DeckTest {

    @Test
    fun fullDeckWith150Cards(){
        fullDeck

        assertEquals(150, fullDeck.cards.size)

        val shuffledDeck = fullDeck.cards.shuffled()

        val randomCard = shuffledDeck[7]
        println("Shuffled card value: ${randomCard.value}")

        val randomBoard = shuffledDeck.take(12)
        println("Shuffled Board:  ${randomBoard.joinToString { it.value.toString() }}")

    }
}