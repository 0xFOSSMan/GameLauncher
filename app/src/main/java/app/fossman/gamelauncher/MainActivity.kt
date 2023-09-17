@file:Suppress("DEPRECATION", "KotlinRedundantDiagnosticSuppress")

package app.fossman.gamelauncher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import app.fossman.gamelauncher.ui.theme.GameLauncherTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameLauncherTheme {
                GameLauncherView(LocalContext.current)
            }
        }
    }

}
