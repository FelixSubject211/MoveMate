package domain

import domain.entities.GameOption

interface GameLogicOpponentInteractor {
    fun makeMove(option: GameOption)
}