package screens.game

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.GameLogicPlayerInteractor
import domain.GameLogicPresenter
import domain.entities.GameOption
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class GameScreenModel(
    gameLogicPresenter: GameLogicPresenter,
    private val gameLogicPlayerInteractor: GameLogicPlayerInteractor,
) : ScreenModel {
    val gameState = gameLogicPresenter.gameState.stateIn(
        screenModelScope,
        SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )


    fun makeMove(option: GameOption) {
       gameLogicPlayerInteractor.makeMove(option = option)
    }

    fun askForNewGame() {
        gameLogicPlayerInteractor.askForNewGame()
    }
}