package di


import domain.impl.DefaultOpponent
import domain.entities.PlayerState
import domain.impl.GameLogicCoordinator
import domain.impl.opponentInteractor
import domain.impl.playerInteractor
import domain.impl.presenter
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.context.startKoin
import org.koin.dsl.module
import screens.game.GameScreenModel
import screens.menu.MenuScreenModel

val gameModule = module {
    factory { MenuScreenModel() }

    factory {
        val playerState = MutableStateFlow<PlayerState>(PlayerState.Ready)
        val opponentState = MutableStateFlow<PlayerState>(PlayerState.Ready)

        val coordinator = GameLogicCoordinator(
            playerState = playerState,
            opponentState = opponentState,
        )

        val opponent = DefaultOpponent(
            gameLogicPresenter = coordinator.presenter(),
            gameLogicOpponentInteractor = coordinator.opponentInteractor()
        )

        opponent.start()

        GameScreenModel(
            gameLogicPresenter = coordinator.presenter(),
            gameLogicPlayerInteractor = coordinator.playerInteractor(),
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
