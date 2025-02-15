package com.fekent.skyjo.engine

import kotlinx.serialization.Serializable



@Serializable
sealed interface GameState {

    @Serializable
    @SerialName("NotStarted")
    class NotStated : GameState {

    }

    @Serializable
    sealed interface StartedGameState : GameState {

        @Serializable
        sealed interface LiveGameState : GameState {


        }

    }
}