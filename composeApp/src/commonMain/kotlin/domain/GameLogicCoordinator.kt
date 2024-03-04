package domain

import domain.entities.GameState
import kotlinx.coroutines.flow.Flow

interface GameLogicCoordinator {
    var gameState: Flow<GameState>

    fun askForNewMatch()
}

