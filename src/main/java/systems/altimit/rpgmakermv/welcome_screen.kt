package systems.altimit.rpgmakermv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.LocalWindowOwner
import androidx.compose.ui.platform.LocalWindowProvider
import androidx.compose.ui.platform.LocalWindowToken
import androidx.compose.ui.platform.LocalWindowType
import androidx.compose.ui.platform.LocalWindowUrlHandler
import androidx.compose.ui.platform.LocalWindowView
import androidx.compose.ui.platform.LocalWindowViewModelStoreOwner
import androidx.compose.ui.platform.LocalWindowViewOwner
import androidx.compose.ui.platform.LocalWindowOnBackPressedDispatcherOwner
import androidx.compose.ui.platform.LocalWindowOnCloseDispatcherOwner

@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Bienvenido a PokemonU!",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { /* Acción para el botón de visitante */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF237A27))
        ) {
            Text("Visitante", style = MaterialTheme.typography.button)
        }

        Button(
            onClick = { /* Acción para el botón de estudiante */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF237A27))
        ) {
            Text("Estudiante", style = MaterialTheme.typography.button)
        }
    }
}

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WelcomeScreen()
        }
    }
}
