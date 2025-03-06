package com.fekent.skyjo.engine

import com.fekent.skyjo.engine.GameState.*
import com.fekent.skyjo.engine.GameAction.*
import com.fekent.skyjo.engine.GameState.StartedGameState.LiveGameState.AwaitingRoundStart
import com.fekent.skyjo.engine.GameState.StartedGameState.LiveGameState.RoundGameState.*
import kotlin.math.round

fun GameState.reduce(action: GameAction): GameState {
    return when (this) {
        is NotStarted -> reduce(action)
        is AwaitingRoundStart -> reduce(action)
        is AwaitingDrawDecision -> reduce(action)
        is AwaitingPlayDecision -> reduce(action)
        is AwaitingFlipDecision -> reduce(action)
        is AwaitingSkyjo -> reduce(action)
    }
}

fun List<GameAction>.reduce(): GameState {
    return fold(GameState.initialGameState) { acc, action -> acc.reduce(action) }
}

private fun NotStarted.reduce(action: GameAction): GameState {
    when (action) {
        is JoinedLobby -> {
            val newAllPlayers = allPlayers + Player(id = action.playerId, name = action.playerName)
            return NotStarted(newAllPlayers)
        }

        is LeftLobby -> {
            val newAllPlayers = allPlayers.filterNot { it.id == action.playerId }
            return NotStarted(newAllPlayers)
        }

        is StartGame -> {

            var deck = Rules.fullDeck
            val boards = mutableMapOf<PlayerId, Board>()

            action.allBoards.entries.forEach { (playerId, _) ->
                val (board, newDeck) = Rules.takeBoardFromDeck(deck)
                deck = newDeck
                boards[playerId] = board
            }

            return AwaitingRoundStart(
                allPlayers = allPlayers.sortedBy { player -> action.playerOrder.indexOf(player.id) },
                allBoards = action.allBoards,
                deck = deck,
                round = 1,
                initialTurn = 1

                //this does NOT deal the cards
            )
        }

        else -> return this
    }
}

private fun AwaitingRoundStart.reduce(action: GameAction): GameState {
    if (action.playerId != turnPlayer.id) return this
    //basically if it's not your turn, wait

    when (action) {
        is InitialFlipCard -> {
            //use allplayers to determine who goes first
            //each player flips over two cards - value of these cards is added up



            return this
        }

        is StartRound -> {
            //if it's the first round, the player with the highest value goes first
            //if it's any other round number, the player who flipped their last card over first
            // starts the round regardless of the scores of the initial flip

            return this
        }

        else -> return this
    }
}