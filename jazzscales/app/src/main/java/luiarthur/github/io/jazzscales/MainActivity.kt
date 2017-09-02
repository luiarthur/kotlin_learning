package luiarthur.github.io.jazzscales

import android.content.res.AssetManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import android.widget.PopupMenu
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    val url = "file:///android_asset/html/template.html"
    private var musicStaffWidth = 400

    //val collections = assets.open("jazz/collections.html")//.bufferedReader().use { it.readText() }.replace("\n","")
    //private val scales = JazzXmlParser.getAllTags(collections, "scale").map{JazzXmlParser.Xml(it)}
    //private val Cdim6 = scales.filter{it.name=="Cdim6"}?.first()?.value

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enable JavaScript calling from html files
        wvScore.settings.javaScriptEnabled = true

        // Method 1
        //val summary = "<html><body>You scored <b>192</b> points.</body></html>"
        //webview.loadData(summary, "text/html", null)

        // Method 2
        wvScore.webViewClient = WebViewClient() // opens in activity, instead of new window
        wvScore.loadUrl(url)
    }

    fun renderMusic(music:String) {
        // sharps: '^' preceding note
        // flats:  '_' preceding note
        wvScore.evaluateJavascript( "var music = `%%staffwidth $musicStaffWidth\n $music`", null)
        wvScore.evaluateJavascript("ABCJS.renderAbc('music', music)", null)
    }

    fun setStaffWidth(staffWidth: Int) {
        musicStaffWidth = staffWidth
    }




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
          ABC
        """)

        //listAssetFiles("jazz")
    }

    fun expandLists(v:View) {
        val popup = PopupMenu(this, v)
        popup.inflate(R.menu.popup_menu)
        popup.show()
    }

    //fun doSomething(v: View) {
    //   TODO()
    //}
}


