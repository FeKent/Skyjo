package com.fekent.skyjo.engine

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val value: Int,
    val colour: CardColor
)