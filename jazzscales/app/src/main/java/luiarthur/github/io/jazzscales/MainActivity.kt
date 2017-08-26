package luiarthur.github.io.jazzscales

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Enable JavaScript calling from html files
        //val webview = WebView(this)
        val webview = findViewById<WebView>(R.id.webViewScore)
        //setContentView(webview)
        val webSettings = webview.getSettings()
        webSettings.setJavaScriptEnabled(true)

        // Method 1
        //val summary = "<html><body>You scored <b>192</b> points.</body></html>"
        //webview.loadData(summary, "text/html", null)

        // Method 2
        // Load url: This file resides in app/src/assets/html/template.html.
        // (Notice the "s" in assets)
        webview.setWebViewClient(WebViewClient()) // opens in activity, instead of new window
        val html = "file:///android_asset/html/template.html"
        webview.loadUrl(html)

        // Method 3 (can't display js?)
        //val html:String = getAssets().open("html/template.html").bufferedReader().use { it.readText() }.replace("\n","")
        //webview.loadData(html, "text/html", null)
    }
}

