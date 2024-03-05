package domain

import domain.entities.GameOption

interface GameLogicPlayerInteractor {
    fun makeMove(option: GameOption)

    fun askForNewGame()
}