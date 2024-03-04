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
import screens.GameScreenModel

val gameModule = module {
    single(named("playerAbility")) { MutableStateFlow<PlayerState>(PlayerState.Ready) }
    single(named("opponentAbility")) { MutableStateFlow<PlayerState>(PlayerState.Ready) }

    single<GameLogicCoordinator> {
        GameLogicDefaultCoordinator(
            playerState = get(named("playerAbility")),
            opponentState = get(named("opponentAbility")),
            opponent = get()
        )
    }

    single<Opponent> {
        DefaultOpponent(
            playerState = get(named("playerAbility")),
            opponentState = get(named("opponentAbility"))
        )
    }

    factory { GameScreenModel(
        gameLogicCoordinator = get(),
        playerState = get(named("playerAbility"))
    ) }
}

fun initKoin() {
    startKoin {
        modules(
            gameModule
        )
    }
}
