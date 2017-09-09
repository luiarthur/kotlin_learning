package luiarthur.github.io.jazzscales

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout.LayoutParams
import android.widget.PopupMenu
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.options_dummy.*
import kotlinx.android.synthetic.main.options_main.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var abcMusic = ""
    private val url = "file:///android_asset/html/template.html"
    private var musicStaffWidth = 400
    private val defaultJazzDataPath = "jazz/jazzData.txt"
    private val internalStorageFilename = "jazzScalesData.txt"

    private lateinit var currentMenu: String
    private lateinit var jazzParser: JazzParser
    private lateinit var currentJazzData: JazzData
    private lateinit var currentList: String

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
        val jazzDataTxt = if (File(filesDir, internalStorageFilename).exists()) {
            Log.d("HERE", "I read file from internal storage")
            Log.d("HERE", "filesDir: " + filesDir.toString())
            //readFromInternalStorage(filesDir.toString() + "/" + internalStorageFile)
            readFromInternalStorage(internalStorageFilename)
        } else {
            Log.d("HERE", "I DID NOT read file from internal storage")
            readResource(defaultJazzDataPath)
        }
        jazzParser = JazzParser(jazzDataTxt)

        // This is how you READ / WRITE to Internal Storage
        //val tmp:String = readResource("jazz/jazzData.txt")
        //writeToInternalStorage("jazzScalesData.txt", tmp)
        //jazzDataTxt = readFromInternalStorage("jazzScalesData.txt")
    }


    fun renderMusic(music: String) {
        // sharps: '^' preceding note
        // flats:  '_' preceding note
        wvScore.evaluateJavascript("var music = `%%staffwidth $musicStaffWidth\n $music`", null)
        wvScore.evaluateJavascript("ABCJS.renderAbc('music', music)", null)
    }

    fun refreshWebViewScore(v: View) {
        renderMusic("""
          L:1/1
          K:C
          $abcMusic
        """)
    }

    fun transpose(music: String): String {
        // music must be in key of C
        // change the Key (K:C)
        // change the music
        TODO()
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

    private fun createHomeButton() {
        val btnHome = Button(this)
        btnHome.text = "Home"
        btnHome.setBackgroundColor(Color.GRAY)
        btnHome.setOnClickListener(View.OnClickListener {
            // Code here executes on main thread after user presses button
            llDummy.removeAllViews()
            llDummy.visibility = View.GONE
            llMainOptions.visibility = View.VISIBLE
        })

        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        llDummy.addView(btnHome, lp)
    }

    private fun createBackButton(f: (View) -> Unit) {
        val btnBack = Button(this)
        btnBack.text = "Back"
        btnBack.setBackgroundColor(Color.GRAY)
        btnBack.setOnClickListener(View.OnClickListener {
            llDummy.removeAllViews()
            f(View(this))
        })
        val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        llDummy.addView(btnBack, lp)
    }


    /* Shows all available scales in `music`
     * Also sets up the OnClick, and OnLongClick listeners
     */
    fun displayMusicOptions(v:View, music: List<JazzData>) {
        llDummy.removeAllViews()
        createHomeButton()
        when (currentMenu) {
            "lists" -> createBackButton{ expandLists(it) }
            "collections" -> createBackButton{ expandCollections(it) }
            else -> Log.d("HERE", "In displayMusicOption: This shouldn't happen!")
        }

        for (m in music.sortedBy { it.name }) {
            val mbtn = Button(this)
            mbtn.text = m.name + " " + m.type
            mbtn.setOnClickListener(View.OnClickListener {
                abcMusic = m.music
                renderMusic(m.music)
            })
            llDummy.addView(mbtn)

            mbtn.setOnLongClickListener(View.OnLongClickListener {
                val popup = PopupMenu(this, v)
                popup.inflate(R.menu.popup_menu)
                popup.show()
                currentJazzData = m
                //Log.d("HERE", jazzData.toString())
                true // required to return true
            })
        }
    }

    //TODO: On click btn, do show stuff
    fun expandLists(v: View) {
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
                val music: List<JazzData> = jazzParser.jazzData.filter { it.list.contains(list) }
                currentList = list
                displayMusicOptions(v, music)
            })

            llDummy.addView(btn, lp)
        }
    }

    //TODO: On click btn, do show stuff
    fun expandCollections(v: View) {
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
                val music: List<JazzData> = jazzParser.getAll(c.toLowerCase())
                displayMusicOptions(v, music)
            })

            llDummy.addView(btn, lp)
        }
    }


    // TODO: Implement these 3 functions
    fun clickedEdit(item: MenuItem) {
        Log.d("Edit is clicked!", currentMenu)
        when (currentMenu){
            "lists" -> run {
                Log.d("HERE", jazzParser.getAllLists().joinToString())
            }
            "collections" -> run {
                Log.d("HERE", currentJazzData.toString())
            }
        }
    }

    fun clickedAdd(item: MenuItem) {
        Log.d("Add is clicked!", currentMenu)
        //restoreAppDefauls()
    }

    // TODO:
    // 1. Add dialog box to confirm
    // 2. Refresh screen after delete
    fun clickedRemove(item: MenuItem) {
        Log.d("Remove is clicked!", currentMenu)
        when (currentMenu) {
            "lists" -> run {
                jazzParser = jazzParser.rmFromList(currentJazzData.type, currentJazzData.name, currentList)
            }
            "collections" -> run {
                jazzParser = jazzParser.rmElement(currentJazzData.type, currentJazzData.name)
            }
            else -> Log.d("HERE", "Shouldn't be here!")
        }
        writeToInternalStorage(filename=internalStorageFilename, text=jazzParser.text)
        val tmp = readFromInternalStorage(internalStorageFilename)
        Log.d("HERE", "\n" + tmp)
    }

    fun restoreAppDefaults(v:View) {
        val file = File(filesDir, internalStorageFilename)
        file.delete()
        Log.d("HERE", "Deleted the file")
        val jazzDataTxt = readResource(defaultJazzDataPath)
        jazzParser = JazzParser(jazzDataTxt)
    }
}

