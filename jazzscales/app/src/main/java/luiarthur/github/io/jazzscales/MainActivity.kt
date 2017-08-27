package luiarthur.github.io.jazzscales

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    val url = "file:///android_asset/html/template.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Enable JavaScript calling from html files
        //val webview = WebView(this)
        val webview = findViewById<WebView>(R.id.webViewScore)
        //setContentView(webview)
        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true

        // Method 1
        //val summary = "<html><body>You scored <b>192</b> points.</body></html>"
        //webview.loadData(summary, "text/html", null)

        // Method 2
        // Load url: This file resides in app/src/assets/html/template.html.
        // (Notice the "s" in assets)
        webview.setWebViewClient(WebViewClient()) // opens in activity, instead of new window
        webview.loadUrl(url)
        // Trying to change the music. Not Working???
        //webview.evaluateJavascript("""
        //  var music = `
        //    %%staffwidth 400
        //    L:1/1
        //    [DE][DF][EG]
        //    `;
        //  ABCJS.renderAbc('music',music)}""", null)

        // Method 3 (can't display js?)
        //val html:String = assets.open("html/template.html").bufferedReader().use { it.readText() }.replace("\n","")
        //webview.loadData(html, "text/html", null)

    }

    fun refreshWebViewScore(v: View) {
        val webview = findViewById<WebView>(R.id.webViewScore)
        webview.evaluateJavascript("""
          var newMusic = `
            %%staffwidth 400
            L:1/1
            [DE][DF][EG] |
            `;
          ABCJS.renderAbc('music',newMusic)""", null)

        val tv = findViewById<TextView>(R.id.textView)
        tv.text = "Here's new music!"
    }
}

