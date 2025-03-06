package com.fekent.skyjo.engine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
sealed interface GameState {

    companion object {
        val initialGameState: GameState = NotStarted(allPlayers = emptyList())
    }

    val allPlayers: List<Player>

    val allPlayerIds: List<PlayerId> get() = allPlayers.map(Player::id)

    val allPlayerNames: List<String> get() = allPlayers.map(Player::name)

    fun getPlayer(playerId: PlayerId): Player? {
        return allPlayers.find { it.id == playerId }
    }

    fun playerName(playerId: PlayerId): String? {
        return allPlayers.find { it.id == playerId }?.name
    }

    @Serializable
    @SerialName("NotStarted")
    class NotStarted(override val allPlayers: List<Player>) : GameState {
        override fun toString() = "NotStarted"
    }

    @Serializable
    sealed interface StartedGameState : GameState {

        override val allPlayers: List<Player>
        val allBoards: Map<PlayerId, Board>
        val deck: Deck

        @Serializable
        sealed interface LiveGameState : StartedGameState {


            val round: Int
            val initialTurn: Int

            val turnPlayer: Player get() = allPlayers[(initialTurn - 1) % allPlayers.size]

            @Serializable
            @SerialName("AwaitingRoundStart")
            class AwaitingRoundStart(
                override val allPlayers: List<Player>,
                override val allBoards: Map<PlayerId, Board>,
                override val deck: Deck,
                override val round: Int,
                override val initialTurn: Int,
                //wait for everyone to flip over their initial two cards
                //then whoever had the highest flip total would go first OR who finished previous round
                //this starts the round!
            ) : LiveGameState

            @Serializable
            sealed interface RoundGameState : GameState{

                val turn: Int

                @Serializable
                @SerialName("AwaitingDrawDecision")
                class AwaitingDrawDecision(
                    override val allPlayers: List<Player>,
                    override val turn: Int
                    //wait for player to choose either the revealed (pile) or unrevealed card (deck)
                ): RoundGameState

                @Serializable
                @SerialName("AwaitingPlayDecision")
                class AwaitingPlayDecision(
                    override val allPlayers: List<Player>,
                    override val turn: Int
                    //wait for player to decided whether to keep drawn card and where it's placed
                    //OR whether to discard the drawn card and flip an unrevealed card in hand
                    //either choice means a card will be put in the pile
                ): RoundGameState

                @Serializable
                @SerialName("AwaitingFlipDecision")
                class AwaitingFlipDecision(
                    override val allPlayers: List<Player>,
                    override val turn: Int
                    //wait for player to decide position of new card on their board
                    //new card will either be the drawn card or one from the players hand
                ): RoundGameState

                @Serializable
                @SerialName("AwaitingSkyjo")
                class AwaitingSkyjo(
                    override val allPlayers: List<Player>,
                    override val turn: Int
                    //if player's new card means that there is a column of REVEALED same numbers
                    //then that row will ALL be moved onto the pile, with the initial discarded card on the bottom
                ): RoundGameState

            }
        }
    }
}