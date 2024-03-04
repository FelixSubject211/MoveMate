package domain.impl

import domain.Opponent
import domain.entities.GameOption
import domain.entities.PlayerState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class DefaultOpponent(
    private val playerState: MutableStateFlow<PlayerState>,
    private val opponentState: MutableStateFlow<PlayerState>
): Opponent {
    init {
        CoroutineScope(Dispatchers.Main).launch {
            playerState
                .collect { player ->
                    if (player is PlayerState.Shaking) {
                        delay(400)
                        makeMove()
                    }
                }
        }
    }

    private fun makeMove() {
        opponentState.tryEmit(PlayerState.Shaking)

        CoroutineScope(Dispatchers.Main).launch {
            delay(3000)
            val option: GameOption = GameOption.entries.toTypedArray().random()
            opponentState.tryEmit(PlayerState.Finished(option))
        }
    }

    override fun askForNewMatch(): Boolean {
        return opponentState.value is PlayerState.Finished
    }
}