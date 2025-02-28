package com.fekent.skyjo.engine

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Board(val cards: List<Card>){

    fun startingBoard(initialCards: List<Card>): Board {
        return Board(
            cards = buildList {
                var cardIndex = 0

                for (i in 0 until Rules.initialBoardSize) {
                    if (i < cards.size) {
                        add(cards[i])
                    } else {
                        add(initialCards[cardIndex])
                        cardIndex++
                    }
                }
            }
        )
    }
}