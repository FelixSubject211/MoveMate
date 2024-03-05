package domain

import domain.entities.GameState
import kotlinx.coroutines.flow.Flow

interface GameLogicPresenter {
    var gameState: Flow<GameState>
}