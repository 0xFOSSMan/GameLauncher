@file:Suppress("DEPRECATION")

package app.fossman.gamelauncher

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

// https://stackoverflow.com/questions/31600256/how-to-check-if-the-app-is-game-or-not-programmatically/31600301#31600301
fun packageIsGame(context: Context, packageName: String): Boolean {
    return try {
        val info: ApplicationInfo =
            context.packageManager.getApplicationInfo(packageName, 0)
        info.category == ApplicationInfo.CATEGORY_GAME
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}
