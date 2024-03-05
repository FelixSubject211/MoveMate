package screens.game

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.GameLogicCoordinator
import domain.entities.GameOption
import domain.entities.PlayerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameScreenModel(
    private val gameLogicCoordinator: GameLogicCoordinator,
    private val playerState: MutableStateFlow<PlayerState>
) : ScreenModel {

    val gameState = gameLogicCoordinator.gameState.stateIn(
        screenModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )


    fun makeMove(option: GameOption) {
        playerState.tryEmit(PlayerState.Shaking)

        screenModelScope.launch {
            delay(3000)
            playerState.tryEmit(PlayerState.Finished(option))
        }
    }

    fun askForNewGame() {
        gameLogicCoordinator.askForNewMatch()
    }
}