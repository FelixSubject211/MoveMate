package domain.impl

import domain.GameLogicCoordinator
import domain.Opponent
import domain.entities.GameState
import domain.entities.PlayerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

class GameLogicDefaultCoordinator(
    private val playerState: MutableStateFlow<PlayerState>,
    private val opponentState: MutableStateFlow<PlayerState>,
    private val opponent: Opponent
) : GameLogicCoordinator {

    override var gameState: Flow<GameState> =
        playerState.combine(opponentState) { player, opponent ->
            GameState(player = player, opponent =  opponent)
        }


    override fun askForNewMatch() {
        if (opponent.askForNewMatch()) {
            playerState.tryEmit(PlayerState.Ready)
            opponentState.tryEmit(PlayerState.Ready)
        }
    }
}
