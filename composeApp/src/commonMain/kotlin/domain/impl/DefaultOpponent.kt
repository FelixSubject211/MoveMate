package domain.impl

import domain.GameLogicOpponentInteractor
import domain.GameLogicPresenter
import domain.Opponent
import domain.entities.GameOption
import domain.entities.PlayerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


class DefaultOpponent(
    private val gameLogicPresenter: GameLogicPresenter,
    private val gameLogicOpponentInteractor: GameLogicOpponentInteractor
): Opponent {

    override fun start() {
        CoroutineScope(Dispatchers.Main).launch {
            gameLogicPresenter.gameState
                .distinctUntilChanged{ old, new ->
                    old.player == new.player
                }
                .collect { gameState ->
                    if (gameState.player is PlayerState.Shaking) {
                        delay(400)
                        val randomOption: GameOption = GameOption.entries.toTypedArray().random()
                        gameLogicOpponentInteractor.makeMove(randomOption)
                    }
                }
        }
    }
}