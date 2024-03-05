package domain.impl

import domain.GameLogicOpponentInteractor
import domain.GameLogicPlayerInteractor
import domain.GameLogicPresenter
import domain.entities.GameOption
import domain.entities.GameState
import domain.entities.PlayerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class GameLogicCoordinator(
    val playerState: MutableStateFlow<PlayerState>,
    val opponentState: MutableStateFlow<PlayerState>,
)

fun GameLogicCoordinator.presenter(): GameLogicPresenter {
    return object : GameLogicPresenter {
        override var gameState: Flow<GameState> =
            combine(playerState, opponentState) { player, opponent ->
                GameState(player = player, opponent =  opponent) }
    }
}

fun GameLogicCoordinator.playerInteractor(): GameLogicPlayerInteractor {
    return object : GameLogicPlayerInteractor {
        override fun makeMove(option: GameOption) {
            playerState.tryEmit(PlayerState.Shaking)

            CoroutineScope(Dispatchers.Default).launch {
                delay(3000)
                playerState.tryEmit(PlayerState.Finished(option))
            }
        }

        override fun askForNewGame() {
            playerState.tryEmit(PlayerState.Ready)
            opponentState.tryEmit(PlayerState.Ready)
        }
    }
}

fun GameLogicCoordinator.opponentInteractor(): GameLogicOpponentInteractor {
    return object : GameLogicOpponentInteractor {
        override fun makeMove(option: GameOption) {
            opponentState.tryEmit(PlayerState.Shaking)

            CoroutineScope(Dispatchers.Default).launch {
                delay(3000)
                opponentState.tryEmit(PlayerState.Finished(option))
            }
        }
    }
}




