package luiarthur.github.io.jazzscales

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val url = "file:///android_asset/html/template.html"
    private var musicStaffWidth = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enable JavaScript calling from html files
        webViewScore.settings.javaScriptEnabled = true

        // Method 1
        //val summary = "<html><body>You scored <b>192</b> points.</body></html>"
        //webview.loadData(summary, "text/html", null)

        // Method 2 (can't display js?)
        // Load url: This file resides in app/src/assets/html/template.html.
        // (Notice the "s" in assets)
        //val html:String = assets.open("html/template.html").bufferedReader().use { it.readText() }.replace("\n","")
        //webview.loadData(html, "text/html", null)

        // Method 3
        webViewScore.webViewClient = WebViewClient() // opens in activity, instead of new window
        webViewScore.loadUrl(url)
    }

    fun renderMusic(music:String) {
        // sharps: '^' preceding note
        // flats:  '_' preceding note
        webViewScore.evaluateJavascript( "var music = `%%staffwidth $musicStaffWidth\n $music`", null)
        webViewScore.evaluateJavascript("ABCJS.renderAbc('music', music)", null)
    }

    fun setStaffWidth(staffWidth: Int) {
        musicStaffWidth = staffWidth
    }

    val CmajDim6 = "CDEFG^GABc"

    fun transpose(music: String): String{
        // music must be in key of C
        // change the Key (K:C)
        // change the music
        TODO()
    }

    fun refreshWebViewScore(v:View) {
        //renderMusic("""
        //    L: 1/1
        //    K: C
        //    [V:1 clef=treble] [DG][FC][E^A] |
        //    [V:2 clef=bass]   [E,B,][D,A,][C,G,] |
        //""")
        renderMusic("""
          L:1/1
          K:C
          $CmajDim6
        """)

        textView.text = "Here's new music!"
    }
}

