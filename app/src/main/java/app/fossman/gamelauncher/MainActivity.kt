@file:Suppress("DEPRECATION")

package app.fossman.gamelauncher

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column (
                verticalArrangement = Arrangement.SpaceAround
            ){
                Text(text = "GameLauncher", modifier = Modifier.align(Alignment.CenterHorizontally))
                val pm: PackageManager = packageManager
                val sb = StringBuilder()
                val appsList = ArrayList<ApplicationInfo>()
                val i = Intent(Intent.ACTION_MAIN, null)
                val allApps = pm.queryIntentActivities(i, 0)
                val tag = "GameLauncher"

                i.addCategory(Intent.CATEGORY_LAUNCHER)
                //https://stackoverflow.com/questions/31600256/how-to-check-if-the-app-is-game-or-not-programmatically/31600301#31600301
                fun packageIsGame(context: Context, packageName: String): Boolean {
                    return try {
                        val info: ApplicationInfo =
                            context.packageManager.getApplicationInfo(packageName, 0)
                        info.category == ApplicationInfo.CATEGORY_GAME
                    } catch (e: PackageManager.NameNotFoundException) {
                        false
                    }
                }

                for (ri in allApps) {
                    val app = ApplicationInfo()
                    app.name = ri.loadLabel(pm).toString()
                    app.packageName = ri.activityInfo.packageName
                    if(packageIsGame(LocalContext.current, app.packageName)){
                        if (!appsList.contains(app)){
                            appsList.add(app)
                            Log.v(tag, "Added App: ".plus(app.packageName))
                        }
                    }
                }

                sb.append(appsList.size)
                sb.append(" apps loaded")
                Toast.makeText(
                    this@MainActivity,
                    sb.toString(),
                    Toast.LENGTH_SHORT
                ).show()

                LazyColumn {
                    items(appsList) { app ->
                        ClickableText(AnnotatedString(app.name), modifier = Modifier.padding(15.dp), onClick ={
                            val launchIntent: Intent? = pm.getLaunchIntentForPackage(app.packageName)
                            startActivity(launchIntent)
                        })
                        Divider()
                    }
                }

            }
        }

    }
}
