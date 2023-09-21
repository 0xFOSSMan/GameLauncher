package app.fossman.gamelauncher

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("DEPRECATION")
@Composable
fun GameLauncherView(context: Context) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "GameLauncher",
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                color = MaterialTheme.colorScheme.primary,
            )
            val packageManager = LocalContext.current.packageManager
            val pm: PackageManager = packageManager
            val sb = StringBuilder()
            val appsList = ArrayList<ApplicationInfo>()
            val gamePackageList = ArrayList<String>()
            val i = Intent(Intent.ACTION_MAIN, null)
            val allApps = pm.queryIntentActivities(i, 0)
            val tag = "GameLauncher"
            var isContextMenuVisible = false
            // var pressOffset: DpOffset

            gameListOverridePopulate()

            i.addCategory(Intent.CATEGORY_LAUNCHER)

            for (ri in allApps) {
                val app = ApplicationInfo()
                app.name = ri.loadLabel(pm).toString()
                app.packageName = ri.activityInfo.packageName
                if (packageIsGame(LocalContext.current, app.packageName) || gameListOverride.contains(app.packageName)) {
                    if (gamePackageList.contains(app.packageName)) {
                        Log.v(tag, "duplicate package ".plus(app.packageName))
                    } else {
                        appsList.add(app)
                        Log.v(
                            tag,
                            "Added App: ".plus(app.packageName).plus(" ")
                                .plus(pm.getLaunchIntentForPackage(app.packageName)),
                        )
                    }
                    gamePackageList.add(app.packageName)
                } else {
                    Log.v(tag, "App not added: ".plus(app.packageName))
                }
            }

            sb.append(appsList.size)
            sb.append(" apps loaded")
            Toast.makeText(
                context,
                sb.toString(),
                Toast.LENGTH_SHORT,
            ).show()
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.padding(15.dp),
                content = {
                    items(appsList.size) { app ->
                        Card(
                            modifier = Modifier
                                .padding(10.dp)
                                .pointerInput(true) {
                                    detectTapGestures(onLongPress = {
                                        isContextMenuVisible = true
                                        // pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                                    })
                                },
                            onClick = {
                                val launchIntent: Intent? =
                                    pm.getLaunchIntentForPackage(appsList[app].packageName)
                                launchIntent(context, launchIntent!!)
                            },
                        ) {
                            DropdownMenu(
                                expanded = isContextMenuVisible,
                                onDismissRequest = { isContextMenuVisible = false },
                            ) {
                                DropdownMenuItem(text = { "Add to favorite" }, onClick = {
                                    Toast.makeText(
                                        context,
                                        "Feature not done yet",
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                })
                                DropdownMenuItem(text = { "Uninstall" }, onClick = {
                                    val uninstallIntent = Intent(Intent.ACTION_DELETE)
                                    uninstallIntent.data = Uri.parse("package:" + appsList[app].packageName)
                                    launchIntent(context, uninstallIntent)
                                })
                            }
                            /*IconButton(
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .padding(0.dp, 0.dp, 0.dp, 0.dp),
                                onClick = {
                                /*TODO*/
                                }
                            ) {
                                Image(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
                            }
                             */
                            // creating the bitmap and resizing it
                            val b: Bitmap = Bitmap.createBitmap(pm.getActivityIcon(pm.getLaunchIntentForPackage(appsList[app].packageName)!!).toBitmap())
                            val rb: Bitmap = Bitmap.createScaledBitmap(b, 128, 128, false)
                            Image(
                                rb.asImageBitmap(),
                                appsList[app].packageName,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(0.dp, 10.dp, 0.dp, 0.dp),
                            )
                            Text(
                                AnnotatedString(
                                    appsList[app].name,
                                    spanStyle = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                    ),
                                    paragraphStyle = ParagraphStyle(TextAlign.Center),
                                ),
                                modifier = Modifier
                                    .padding(0.dp, 0.dp, 0.dp, 10.dp)
                                    .align(Alignment.CenterHorizontally)
                                    .fillMaxWidth(),
                            )
                        }
                    }
                },
            )
        }
    }
}
