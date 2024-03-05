package di


import domain.GameLogicCoordinator
import domain.Opponent
import domain.impl.DefaultOpponent
import domain.entities.PlayerState
import domain.impl.GameLogicDefaultCoordinator
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import screens.game.GameScreenModel
import screens.menu.MenuScreenModel

val gameModule = module {
    factory { MenuScreenModel() }

    factory {
        val playerState = MutableStateFlow<PlayerState>(PlayerState.Ready)
        val opponentState = MutableStateFlow<PlayerState>(PlayerState.Ready)

        val opponent: Opponent = DefaultOpponent(
            playerState = playerState,
            opponentState = opponentState
        )

        val coordinator: GameLogicCoordinator = GameLogicDefaultCoordinator(
            playerState = playerState,
            opponentState = opponentState,
            opponent = opponent
        )

        GameScreenModel(
            gameLogicCoordinator = coordinator,
            playerState = playerState
        )
    }
}

fun initKoin() {
    startKoin {
        modules(
            gameModule
        )
    }
}
