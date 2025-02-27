package com.fekent.skyjo.engine

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val value: Int,
//    val colour: CardColor - this is UI related, not vital for gameplay/rules/engine (add in UI later)
)