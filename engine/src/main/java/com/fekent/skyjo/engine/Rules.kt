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
            }.shuffled()
        )

    const val initialBoardSize: Int = 12

    fun takeBoardFromDeck(deck: Deck): Pair<Board, Deck> {
        val (boardCards, newDeck) = takeTilesFromBag(deck = deck, count = initialBoardSize)
        return Board(boardCards) to newDeck
    }

    fun takeTilesFromBag(deck: Deck, count: Int): Pair<List<Card>, Deck> {
        return with(deck.cards.shuffled()) { take(count) to Deck(drop(count)) }
    }

    fun deal(playerIds: List<PlayerId>): Map<PlayerId, Board> {
        var deck = fullDeck
        val boards = mutableMapOf<PlayerId, Board>()

        for (playerId in playerIds) {
            val (board, newDeck) = takeBoardFromDeck(deck)
            deck = newDeck
            boards[playerId] = board
        }

        return boards
    }

}