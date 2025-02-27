package com.fekent.skyjo.engine

object Rules {

    val fullDeck: Deck =
        Deck(
            buildList {
                //150 cards total
                // 5x -2 cards
                // 15x 0 cards
                // 10x of every other card

                val totalDeck = 150


                repeat(5){ add(Card(-2))}
                repeat(10){ add(Card(-1))}
                repeat(15){add(Card(0))}
                repeat(10){ add(Card(1))}
                repeat(10){ add(Card(2))}
                repeat(10){ add(Card(3))}
                repeat(10){ add(Card(4))}
                repeat(10){ add(Card(5))}
                repeat(10){ add(Card(6))}
                repeat(10){ add(Card(7))}
                repeat(10){ add(Card(8))}
                repeat(10){ add(Card(9))}
                repeat(10){ add(Card(10))}
                repeat(10){ add(Card(11))}
                repeat(10){ add(Card(12))}
            }
        )
}