import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import screens.MainScreen


@Composable
fun App() {
    MaterialTheme {
        Navigator(MainScreen)
    }
}