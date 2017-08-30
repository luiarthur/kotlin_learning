import scala.scalajs.js.annotation._
import scala.scalajs.js

@JSExportTopLevel("jazz")
object jazz{
    @JSExport
    val pianoNotes = 
      js.Array("A", "B", "C", "D", "E", "F", "G")

    @JSExport
    def inPianoNotes(note: String):Boolean = {
      pianoNotes.contains(note)
    }
}
