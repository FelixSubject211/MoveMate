package screens

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel


data object MainScreen : Screen {
    @Composable
    override fun Content() {
        val screenModel: MainScreenModel = getScreenModel()

        val text by screenModel.text.collectAsState()

        Text(text)
    }
}