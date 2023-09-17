package app.fossman.gamelauncher

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity

fun launchIntent(context: Context, intent: Intent) {
    startActivity(context, intent, null)
}
