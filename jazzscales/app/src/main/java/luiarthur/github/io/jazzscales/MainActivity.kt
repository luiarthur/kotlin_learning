package luiarthur.github.io.jazzscales

import android.content.Context
import android.content.res.AssetManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.PopupMenu
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.nio.file.Files.delete
import android.widget.LinearLayout
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.options_choose.*
import kotlinx.android.synthetic.main.options_main.*


class MainActivity : AppCompatActivity() {

    private val url = "file:///android_asset/html/template.html"
    private var musicStaffWidth = 400

    private lateinit var currentMenu: String
    private lateinit var jazzParser: JazzParser

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

        // Initialize jazz collections and lists
        val jazzDataTxt = readResource("jazz/jazzData.txt")
        jazzParser = JazzParser(jazzDataTxt)
        //collections = readResource("jazz/collections.xml")
        //lists = readResource("jazz/lists.xml")

        Log.d("Bla", filesDir.toString())

        // This is how you READ / WRITE to Internal Storage
        //val tmp:String = readResource("jazz/jazzData.txt")
        //writeToInternalStorage("jazzScalesData.txt", tmp)
        //jazzDataTxt = readFromInternalStorage("jazzScalesData.txt")
//        llChoose.visibility = View.GONE
    }


    private fun loadJazzParser() {
        val jazzDataTxt = readResource("jazz/jazzData.txt")
        jazzParser = JazzParser(jazzDataTxt)
    }

    fun renderMusic(music: String) {
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

        //val scales = JazzXmlParser.getAllTags(collections, "scale").map{JazzXmlParser.Xml(it)}
        val scales = jazzParser.getAll("scale")
        val Cdim6:String = scales.filter{it.name=="Cdim6"}.first().music

        renderMusic("""
          L:1/1
          K:C
          $Cdim6
        """)
    }


    //fun doSomething(v: View) {
    //   TODO()
    //}

    // read text file relative to assets dir
    private fun readResource(path: String):String {
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

    private fun writeToInternalStorage(filename:String, text:String) {
        val outputStream = openFileOutput(filename, Context.MODE_PRIVATE)
        outputStream.write(text.toByteArray())
        outputStream.close()
    }

    private fun readFromInternalStorage(filename:String):String {
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

    fun expandLists(v:View) {
        currentMenu = "lists"
        llSidebar.visibility = View.GONE
        llChoose.visibility = View.VISIBLE
    }

    fun expandCollections(v:View) {
        currentMenu = "collections"
        llChoose.visibility = View.GONE
        llChoose.visibility = View.VISIBLE
    }

    fun clickedSelect(v:View ) {
        //TODO:
        // if currentMenu == lists, list what's in the lists
        // else, list what's in the collections
        Log.d("Select is clicked!", currentMenu)
   //     showSelections()
    }
    fun clickedEdit(v: View) {
        Log.d("Edit is clicked!", currentMenu)
    }
    fun clickedAdd(v: View) {
        Log.d("Add is clicked!", currentMenu)
    }
    fun clickedRemove(v: View) {
        Log.d("Remove is clicked!", currentMenu)
    }

    //fun showSelections() {
    //    val btnScales = Button(this)
    //    val btnChords = Button(this)
    //    val btnCustom = Button(this)

    //    btnScales.text = "Scales"

    //    val ll = llSidebar
    //    val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    //    ll.addView(btnScales, lp)
    //}

    // FIXME
    // See: https://developer.android.com/guide/topics/ui/menus.html
    //fun clickedMenuItem(item: MenuItem): Boolean {
    //    when (item.itemId) {
    //        R.id.Select -> {
    //            Log.d("Select is clicked!", currentMenu)
    //            return true
    //        }
    //        R.id.Remove -> {
    //            Log.d("Remove is clicked!", currentMenu)
    //            return true
    //        }
    //        R.id.Edit-> {
    //            Log.d("Edit is clicked!", currentMenu)
    //            return true
    //        }
    //        R.id.Add -> {
    //            Log.d("Add is clicked!", currentMenu)
    //            return true
    //        }
    //        else -> return false // shouldn't ever happen
    //    }
    //}

}


