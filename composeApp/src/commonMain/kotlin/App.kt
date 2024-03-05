import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import screens.GameScreen


@Composable
fun App() {
    MaterialTheme {
        Navigator(GameScreen)
    }
}