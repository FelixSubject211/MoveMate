package domain.entities

sealed class GameResult {
    data object Tie : GameResult()
    data object PlayerWins : GameResult()
    data object OpponentWins : GameResult()
}