package app.fossman.gamelauncher

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomAppBar() {
    Scaffold(
        bottomBar = {
            BottomAppBar(

            )
        },
    ) { innerPadding ->
        IconButton(onClick = { /*TODO*/ }) {
            Icon(Icons.Filled.Settings, contentDescription = "Settings")
        }
        Text(
            modifier = Modifier.padding(innerPadding),
            text = "Bottom Bar"
        )
    }
}
