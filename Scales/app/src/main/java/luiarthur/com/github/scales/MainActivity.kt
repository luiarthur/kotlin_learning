package luiarthur.com.github.scales

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import java.io.ByteArrayOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val url = "file:///android_asset/index.html"
    private val defaultJazzDataPath = "json/jazzData_orig.json"
    private val internalStorageFilename = "jazzScalesData.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enable JavaScript calling from html files
        wvScore.settings.javaScriptEnabled = true
        wvScore.webViewClient = WebViewClient() // opens in activity, instead of new window
        wvScore.loadUrl(url)
        wvScore.addJavascriptInterface(WebViewJavaScriptInterface(this), "app");
        //wvScore.webChromeClient = WebChromeClient()
    }

    inner class WebViewJavaScriptInterface
    (private val context: Context) {

        @JavascriptInterface
        fun makeToast(message: String, lengthLong: Boolean) {
            Toast.makeText(context, message, if (lengthLong) Toast.LENGTH_LONG else Toast.LENGTH_SHORT).show()
        }

        @JavascriptInterface
        fun readFile():String {
            val s = readResource(defaultJazzDataPath)
            Log.d("HERE", s)
            return s
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

}
