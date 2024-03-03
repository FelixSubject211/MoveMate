package di


import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import screens.MainScreenModel

val dataModule = module {

}

val screenModelsModule = module {
    factoryOf(::MainScreenModel)
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            screenModelsModule,
        )
    }
}