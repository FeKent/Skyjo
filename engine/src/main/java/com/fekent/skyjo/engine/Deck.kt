package com.fekent.skyjo.engine

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Deck(val cards: List<Card>)

