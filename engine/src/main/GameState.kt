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


            @Serializable
            @SerialName("AwaitingInitialFlip")
            class AwaitingInitialFlip(
                //where you wait for everyone to flip over their initial two cards
                //then whoever had the highest flip total would go first
            )


        }

    }
}