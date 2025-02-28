package com.fekent.skyjo.engine

object Rules {

    val fullDeck: Deck =
        Deck(
            buildList {
                //150 cards total
                // 5x -2 cards
                // 15x 0 cards
                // 10x of every other card

                repeat(5) { add(Card(-2)) }
                repeat(15) { add(Card(0)) }

                (-1..12)
                    .filter { it != 0 }
                    .forEach { _ -> repeat(10) { add(Card(it)) } }
            }
        )

    const val initialBoardSize: Int = 12
}