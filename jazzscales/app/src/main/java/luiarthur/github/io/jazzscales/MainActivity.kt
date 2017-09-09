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
import android.widget.LinearLayout.LayoutParams
import android.view.ViewGroup
import android.widget.TextView
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.options_choose.*
import kotlinx.android.synthetic.main.options_main.*
import android.R.attr.button
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.options_dummy.*


class MainActivity : AppCompatActivity() {

    private var abcMusic = ""
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

    fun refreshWebViewScore(v:View) {
        renderMusic("""
          L:1/1
          K:C
          $abcMusic
        """)
    }

    fun transpose(music: String): String{
        // music must be in key of C
        // change the Key (K:C)
        // change the music
        TODO()
    }


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

    fun createHomeButton() {
        val btnHome = Button(this)
        btnHome.text = "Home"
        btnHome.setOnClickListener(View.OnClickListener {
            // Code here executes on main thread after user presses button
            llDummy.removeAllViews()
            llDummy.visibility = View.GONE
            llMainOptions.visibility = View.VISIBLE
        })

        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        llDummy.addView(btnHome, lp)
    }


    //TODO: On click btn, do show stuff
    fun expandLists(v:View) {
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        currentMenu = "lists"
        llMainOptions.visibility = View.GONE
        llDummy.visibility = View.VISIBLE

        val lists = jazzParser.getAllLists()

        createHomeButton()
        for (list in lists) {
            val btn = Button(this)
            btn.text = list

            btn.setOnClickListener(View.OnClickListener {
                // Code here executes on main thread after user presses button
                val music:List<JazzData> = jazzParser.jazzData().filter{it.list.contains(list)}

                llDummy.removeAllViews()
                createHomeButton()

                for (m in music.sortedBy { it.name }) {
                    val mbtn = Button(this)
                    mbtn.text = m.name
                    mbtn.setOnClickListener(View.OnClickListener {
                        abcMusic = m.music
                        renderMusic(m.music)
                    })
                    llDummy.addView(mbtn)
                }
            })

            llDummy.addView(btn, lp)
        }
    }

    //TODO: On click btn, do show stuff
    fun expandCollections(v:View) {
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        currentMenu = "collections"
        llMainOptions.visibility = View.GONE
        llDummy.visibility = View.VISIBLE

        val collections = listOf("Scale", "Chord", "Custom")

        createHomeButton()

        // Create Other Buttons
        for (c in collections) {
            val btn = Button(this)
            btn.text = c
            btn.setOnClickListener(View.OnClickListener {
                // Code here executes on main thread after user presses button
                val music:List<JazzData> = jazzParser.getAll(c.toLowerCase())

                llDummy.removeAllViews()
                createHomeButton()

                for (m in music.sortedBy { it.name }) {
                    val mbtn = Button(this)
                    mbtn.text = m.name
                    mbtn.setOnClickListener(View.OnClickListener {
                        abcMusic = m.music
                        renderMusic(m.music)
                    })
                    llDummy.addView(mbtn)
                }

            })

            llDummy.addView(btn, lp)
        }
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
    fun clickedHome(v: View) {
        Log.d("Home is clicked!", currentMenu)
        llChoose.visibility = View.GONE
        llMainOptions.visibility = View.VISIBLE
    }
}


