package luiarthur.com.github.scales

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import android.webkit.JavascriptInterface
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val url = "file:///android_asset/index.html"
    private val defaultJazzDataPath = "json/jazzData_orig.json" // relative path from file:///android_asset
    private val internalStorageFilename = "jazzScalesData.json" // relative to internal storage for app

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enable JavaScript calling from html files
        wvScore.settings.javaScriptEnabled = true
        wvScore.webViewClient = WebViewClient() // opens in activity, instead of new window
        wvScore.loadUrl(url)
        wvScore.addJavascriptInterface(WebViewJavaScriptInterface(this), "app");
        Log.d("HERE", filesDir.toString())
        //wvScore.setWebChromeClient(WebChromeClient());
    }

    // See Example:
    // https://stackoverflow.com/questions/22895140/call-android-methods-from-javascript
    inner class WebViewJavaScriptInterface
    (private val context: Context) {

        @JavascriptInterface
        fun readFile():String {
            val s = if (File(filesDir, internalStorageFilename).exists()) {
                Log.d("HERE", "reading from internal storage:")
                readFromInternalStorage(internalStorageFilename)
            } else {
                Log.d("HERE", "reading from default path:")
                readResource(defaultJazzDataPath)
            }
            Log.d("HERE", s)
            return s
        }

        @JavascriptInterface
        fun readInternalFile():String {
            return readFromInternalStorage(internalStorageFilename)
        }

        @JavascriptInterface
        fun writeInternalFile(text:String) {
            Log.d("HERE", "this was written to internal\n" + text)
            writeToInternalStorage(internalStorageFilename, text)
        }

        @JavascriptInterface
        fun restoreAppDefaults() {
            val file = File(filesDir, internalStorageFilename)
            file.delete()
            Log.d("HERE", "Deleted the file")
        }

        @JavascriptInterface
        fun printLog(s: String) {
            Log.d("HERE", s)
        }
    }

    // read text file relative to assets dir
    private fun readResource(path: String): String {
        val ins = assets.open(path)
        val baos = ByteArrayOutputStream()
        var i: Int
        try {
            i = ins.read()
            while (i > -1) {
                baos.write(i)
                i = ins.read()
            }
            ins.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return baos.toString()
    }

    private fun writeToInternalStorage(filename: String, text: String) {
        val outputStream = openFileOutput(filename, Context.MODE_PRIVATE)
        outputStream.write(text.toByteArray())
        outputStream.close()
    }

    private fun readFromInternalStorage(filename: String): String {
        val fin = openFileInput(filename)

        var c = fin.read()
        var s = ""
        while (c > -1) {
            c = fin.read()
            s += Character.toString(c.toChar())
        }
        fin.close()

        return s
    }


}
