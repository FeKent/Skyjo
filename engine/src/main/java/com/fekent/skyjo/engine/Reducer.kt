package com.fekent.skyjo.engine

import com.fekent.skyjo.engine.GameAction.InitialFlipCard
import com.fekent.skyjo.engine.GameAction.JoinedLobby
import com.fekent.skyjo.engine.GameAction.LeftLobby
import com.fekent.skyjo.engine.GameAction.StartGame
import com.fekent.skyjo.engine.GameAction.StartRound
import com.fekent.skyjo.engine.GameState.NotStarted
import com.fekent.skyjo.engine.GameState.StartedGameState.LiveGameState.AwaitingRoundStart
import com.fekent.skyjo.engine.GameState.StartedGameState.LiveGameState.RoundGameState.AwaitingDrawDecision
import com.fekent.skyjo.engine.GameState.StartedGameState.LiveGameState.RoundGameState.AwaitingFlipDecision
import com.fekent.skyjo.engine.GameState.StartedGameState.LiveGameState.RoundGameState.AwaitingPlayDecision
import com.fekent.skyjo.engine.GameState.StartedGameState.LiveGameState.RoundGameState.AwaitingSkyjo

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
            val deck = action
                .allBoards
                .entries
                .fold(Rules.fullDeck) { currentDeck, (_, board) ->
                    currentDeck - board.cards
                }

            return AwaitingRoundStart(
                allPlayers = allPlayers.sortedBy { player -> action.players.indexOf(player.id) },
                allBoards = action.allBoards,
                deck = deck,
                round = 1
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