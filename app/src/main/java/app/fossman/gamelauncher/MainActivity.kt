package app.fossman.gamelauncher

import android.R
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import app.fossman.gamelauncher.R.*


class MainActivity : ComponentActivity() {
    private lateinit var listView: ListView
    private lateinit var text: TextView
    private lateinit var gridview: GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.mainactivity)
        gridview = findViewById(id.gridview)
        getAllApps()
        }

    private fun getAllApps() {
        // get list of all the apps installed
        val flags = PackageManager.GET_META_DATA
        val pm = packageManager;
        val packList = pm.getInstalledPackages(flags)
        val apps = arrayOfNulls<String>(packList.size)
        val intents = arrayOfNulls<String>(packList.size)
        val sb = StringBuilder();

        for (i in packList.indices) {
            val packInfo = packList[i]
            apps[i] = packInfo.applicationInfo.loadLabel(packageManager).toString()

        }
        for (j in apps) {
            gridview.adapter = ArrayAdapter(this@MainActivity, R.layout.simple_list_item_1, apps)
        }

        sb.append(packList.size)
        sb.append(" apps loaded")
        Toast.makeText(
            this@MainActivity,
            sb.toString(),
            Toast.LENGTH_SHORT
        ).show()
    }
    override fun onStart() {
        super.onStart()
    }
}