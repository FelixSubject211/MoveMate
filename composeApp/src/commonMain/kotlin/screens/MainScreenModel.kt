package screens

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.random.Random

class MainScreenModel() : ScreenModel {
    val text: StateFlow<String> =
        createRandomNumberFlow().map {
            it.toString()
        }.stateIn(
            scope = screenModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ""
        )

    private fun createRandomNumberFlow(): Flow<Int> {
        return flow {
            while (true) {
                emit(Random.nextInt())
                delay(1000)
            }
        }
    }
}