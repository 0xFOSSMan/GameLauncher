@file:Suppress("DEPRECATION")

package app.fossman.gamelauncher

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import app.fossman.gamelauncher.ui.theme.GameLauncherTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameLauncherTheme {
                Box (
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ){
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "GameLauncher",
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally),
                            color = MaterialTheme.colorScheme.primary
                        )
                        val pm: PackageManager = packageManager
                        val sb = StringBuilder()
                        val appsList = ArrayList<ApplicationInfo>()
                        val gamePackageList = ArrayList<String>()
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
                            if (packageIsGame(LocalContext.current, app.packageName)) {
                                if (gamePackageList.contains(app.packageName)) {
                                    Log.v(tag, "duplicate package ".plus(app.packageName))
                                } else {
                                    appsList.add(app)
                                    Log.v(
                                        tag,
                                        "Added App: ".plus(app.packageName).plus(" ")
                                            .plus(pm.getLaunchIntentForPackage(app.packageName))
                                    )
                                }
                                gamePackageList.add(app.packageName)
                            }
                        }

                        sb.append(appsList.size)
                        sb.append(" apps loaded")
                        Toast.makeText(
                            this@MainActivity,
                            sb.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        if (appsList.size < 5) {
                            LazyColumn(
                                modifier = Modifier
                                    .wrapContentWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                items(appsList) { app ->
                                    Image(
                                        (Bitmap.createBitmap(
                                            pm.getActivityIcon(
                                                pm.getLaunchIntentForPackage(
                                                    app.packageName
                                                )!!
                                            ).toBitmap()
                                        )).asImageBitmap(),
                                        app.packageName
                                    )
                                    ClickableText(
                                        AnnotatedString(
                                            app.name,
                                            spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary),
                                            paragraphStyle = ParagraphStyle(TextAlign.Center)
                                        ),
                                        modifier = Modifier
                                            .padding(15.dp)
                                            .align(Alignment.CenterHorizontally)
                                            .fillMaxWidth(),
                                        onClick = {
                                            val launchIntent: Intent? =
                                                pm.getLaunchIntentForPackage(app.packageName)
                                            startActivity(launchIntent)
                                        }
                                    )
                                    Divider()
                                }
                            }
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(3),
                                verticalArrangement = Arrangement.SpaceAround,
                                modifier = Modifier
                                    .padding(15.dp),
                                content = {
                                items(appsList.size) { app ->
                                    Card(
                                        modifier = Modifier
                                            .padding(15.dp)
                                    ) {
                                        Image(
                                            (Bitmap.createBitmap(
                                                pm.getActivityIcon(
                                                    pm.getLaunchIntentForPackage(
                                                        appsList[app].packageName
                                                    )!!
                                                ).toBitmap()
                                            )).asImageBitmap(),
                                            appsList[app].packageName
                                        )
                                        ClickableText(
                                            AnnotatedString(
                                                appsList[app].name,
                                                spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary),
                                                paragraphStyle = ParagraphStyle(TextAlign.Center)
                                            ),
                                            modifier = Modifier
                                                .padding(0.dp, 0.dp, 0.dp, 20.dp)
                                                .align(Alignment.CenterHorizontally)
                                                .fillMaxWidth(),
                                            onClick = {
                                                val launchIntent: Intent? =
                                                    pm.getLaunchIntentForPackage(appsList[app].packageName)
                                                startActivity(launchIntent)
                                            }
                                        )
                                    }
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}
