package screens.game

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.entities.GameOption
import domain.entities.GameResult
import domain.entities.GameState
import domain.entities.PlayerState

data object GameScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel: GameScreenModel = getScreenModel()
        val gameState = screenModel.gameState.collectAsState()

        Scaffold(
            topBar = {
                TopAppBar(backgroundColor = Color.White) {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                }
            },
            modifier = Modifier,
        ) {
            gameState.value?.let { currentGameState ->
                GameView(gameState = currentGameState, screenModel = screenModel)
            }
        }
    }

    @Composable
    private fun GameView(gameState: GameState, screenModel: GameScreenModel) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            PlayerStateLabel(gameState.opponent, gameState)
            Spacer(modifier = Modifier.height(60.dp))
            PlayerStateLabel(gameState.player, gameState)
            Spacer(modifier = Modifier.height(60.dp))
            GameInfoLabel(gameState)
            Spacer(modifier = Modifier.height(60.dp))

            Box(
                modifier = Modifier
                    .height(80.dp)
            ) {
                when (gameState.player) {
                    is PlayerState.Ready -> {
                        MakeMoveButtons { option -> screenModel.makeMove(option = option) }
                    }

                    is PlayerState.Shaking -> {}

                    is PlayerState.Finished -> {
                        NewGameButton { screenModel.askForNewGame() }
                    }
                }
            }
        }
    }

    @Composable
    fun PlayerStateLabel(
        playerState: PlayerState,
        gameState: GameState
    ) {
        val shakeOffset = if (playerState is PlayerState.Shaking) {
            animateShake()
        } else {
            0.dp
        }

        Box(modifier = Modifier.graphicsLayer(translationX = shakeOffset.value).size(50.dp)) {
            if (
                gameState.gameStateState is GameState.GameStateState.Shaking ||
                gameState.gameStateState is GameState.GameStateState.Waiting
            ) {
                Text(text = PlayerState.Shaking.toEmoji(), style = MaterialTheme.typography.h5)
            } else {
                Text(text = playerState.toEmoji(), style = MaterialTheme.typography.h5)
            }
        }
    }


    @Composable
    private fun MakeMoveButtons(onClick: (GameOption) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            GameOption.entries.forEach { option ->
                GameOptionButton(option = option, onClick = onClick)
            }
        }
    }

    @Composable
    private fun GameInfoLabel(gameState: GameState) {
        Text(gameState.description())
    }

    @Composable
    private fun NewGameButton(onClick: () -> Unit) {
        Button(onClick = onClick, modifier = Modifier.padding(16.dp)) {
            Text("New Game")
        }
    }

    @Composable
    private fun GameOptionButton(option: GameOption, onClick: (GameOption) -> Unit) {
        Button(onClick = { onClick(option) }) {
            Text(text = option.toEmoji(), fontSize = 24.sp)
        }
    }

    @Composable
    fun animateShake(): Dp {
        val infiniteTransition = rememberInfiniteTransition()

        val shakeOffset by infiniteTransition.animateFloat(
            initialValue = -50f,
            targetValue = 50f,
            animationSpec = infiniteRepeatable(
                animation = tween(200, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            )
        )

        return shakeOffset.dp
    }

    private fun GameState.description(): String {
        return when(this.gameStateState) {
            is GameState.GameStateState.Ready -> "Ready"
            is GameState.GameStateState.Shaking -> "Shaking"
            is GameState.GameStateState.Waiting -> "Waiting"
            is GameState.GameStateState.Finish -> {
                when ((this.gameStateState as GameState.GameStateState.Finish).result) {
                    GameResult.Tie -> "It's a tie!"
                    GameResult.PlayerWins -> "You win!"
                    GameResult.OpponentWins -> "Opponent wins!"
                }
            }
        }
    }

    private fun PlayerState.toEmoji(): String {
        return when (this) {
            is PlayerState.Ready, is PlayerState.Shaking -> "✊"
            is PlayerState.Finished -> {
                option.toEmoji()
            }
        }
    }

    private fun GameOption.toEmoji(): String {
        return when (this) {
            GameOption.SCISSORS -> "✌️"
            GameOption.ROCK -> "👊"
            GameOption.PAPER -> "🤚"
        }
    }
}
