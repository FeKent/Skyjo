package com.fekent.skyjo.engine

import com.fekent.skyjo.engine.GameState.*
import com.fekent.skyjo.engine.GameAction.*
import com.fekent.skyjo.engine.GameState.StartedGameState.LiveGameState.AwaitingRoundStart
import com.fekent.skyjo.engine.GameState.StartedGameState.LiveGameState.RoundGameState.*

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
            val deck =
                action
                    .allBoards
                    .entries
                    .fold(Rules.fullDeck.cards.shuffled()) { deck, (_, board) ->
                    deck - board.cards.toSet()
                    }

            return AwaitingRoundStart(
                allPlayers = allPlayers.sortedBy { player -> action.players.indexOf(player.id) },


            )
        }

        else -> return this
    }
}

private fun AwaitingRoundStart.reduce(action: GameAction): GameState {
    when (action) {
        is InitialFlipCard -> {
            return this
        }

        is StartRound -> {
            return this
        }

        else -> return this
    }
}