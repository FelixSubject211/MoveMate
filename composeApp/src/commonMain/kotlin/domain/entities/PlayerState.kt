package domain.entities

sealed class PlayerState {
    data object Ready : PlayerState()
    data object Shaking : PlayerState()
    data class Finished(val option: GameOption) : PlayerState()
}