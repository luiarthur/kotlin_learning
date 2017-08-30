import scala.scalajs.js.annotation._
import scala.scalajs.js

@JSExportTopLevel("Jazz")
object Jazz{
    @JSExport
    val pianoNotes = 
      js.Array("A", "B", "C", "D", "E", "F", "G")

    @JSExport
    def inPianoNotes(note: String):Boolean = {
      pianoNotes.contains(note)
    }

}

@ScalaJSDefined @JSExportTopLevel("Bob")
class Bob(val x:Int, val y:js.Array[Int]) extends js.Object {
  def sum() = y.sum + x
}

