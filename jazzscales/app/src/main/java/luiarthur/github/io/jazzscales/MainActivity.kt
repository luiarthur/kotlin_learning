package luiarthur.github.io.jazzscales

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webview = WebView(this)
        setContentView(webview)

        // Enable JavaScript calling from html files
        val webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Old Example
        //val summary = "<html><body>You scored <b>192</b> points.</body></html>"
        //webview.loadData(summary, "text/html", null)

        // Load url: This file resides in app/src/assets/html/template.html.
        // (Notice the "s" in assets)
        val url = "file:///android_asset/html/template.html"
        webview.loadUrl(url)
    }
}

