package com.fekent.skyjo.engine

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Deck(val cards: List<Card>)

operator fun Deck.minus(cards: List<Card>): Deck {
    return Deck(this.cards - cards.toSet())
}