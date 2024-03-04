package domain.entities

data class GameState(
    val player: PlayerState,
    val opponent: PlayerState
) {

    val gameStateState: GameStateState by lazy {
        determineGameStateState(player, opponent)
    }
    sealed class GameStateState {
        data object Ready : GameStateState()
        data object Shaking: GameStateState()
        data object Waiting: GameStateState()
        data class Finish(val result: GameResult) : GameStateState()
    }

    private fun determineGameStateState(player: PlayerState, opponent: PlayerState): GameStateState {
        return when {
            player is PlayerState.Finished && opponent is PlayerState.Finished -> {
                GameStateState.Finish(determineWinner(player.option, opponent.option))
            }
            player is PlayerState.Shaking || opponent is PlayerState.Shaking -> {
                GameStateState.Shaking
            }
            (player is PlayerState.Finished && opponent is PlayerState.Ready) ||
                    (player is PlayerState.Ready && opponent is PlayerState.Finished) -> {
                GameStateState.Waiting
            }
            else -> GameStateState.Ready
        }
    }

    private fun determineWinner(player: GameOption, opponent: GameOption): GameResult {
        return when {
            player == opponent -> GameResult.Tie
            (player == GameOption.SCISSORS && opponent == GameOption.PAPER) ||
                    (player == GameOption.ROCK && opponent == GameOption.SCISSORS) ||
                    (player == GameOption.PAPER && opponent == GameOption.ROCK) -> GameResult.PlayerWins
            else -> GameResult.OpponentWins
        }
    }
}