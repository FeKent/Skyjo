package com.fekent.skyjo.engine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed interface GameState {

    @Serializable
    @SerialName("NotStarted")
    class NotStarted : GameState {

    }

    @Serializable
    sealed interface StartedGameState : GameState {

        @Serializable
        sealed interface LiveGameState : StartedGameState {

            @Serializable
            @SerialName("AwaitingRoundStart")
            class AwaitingRoundStart(
                //wait for everyone to flip over their initial two cards
                //then whoever had the highest flip total would go first OR who finished previous round
                //this starts the round!
            ) : LiveGameState

            @Serializable
            sealed interface RoundGameState : GameState{


                @Serializable
                @SerialName("AwaitingDrawDecision")
                class AwaitingDrawDecision(
                    //wait for player to choose either the revealed (pile) or unrevealed card (deck)
                ): LiveGameState

                @Serializable
                @SerialName("AwaitingPlayDecision")
                class AwaitingPlayDecision(
                    //wait for player to decided whether to keep drawn card and where it's placed
                    //OR whether to discard the drawn card and flip an unrevealed card in hand
                    //either choice means a card will be put in the pile
                ): LiveGameState

                @Serializable
                @SerialName("AwaitingFlipDecision")
                class AwaitingFlipDecision(
                    //wait for player to decide position of new card on their board
                    //new card will either be the drawn card or one from the players hand
                ): LiveGameState

                @Serializable
                @SerialName("AwaitingSkyjo")
                class AwaitingSkyjo(
                    //if player's new card means that there is a column of REVEALED same numbers
                    //then that row will ALL be moved onto the pile, with the initial discarded card on the bottom
                ): LiveGameState

            }
        }
    }
}