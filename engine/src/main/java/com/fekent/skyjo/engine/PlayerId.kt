package com.fekent.skyjo.engine

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class PlayerId(val rawId: String)