import scala.scalajs.js.annotation._
import scala.scalajs.js
//import scala.scalajs.js.annotation.JSExportTopLevel
import org.scalajs.jquery.jQuery

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
  override def toString() = {
    s"x: ${x.toString};  y: [${y.toString}]"
  }

  def sum() = y.sum + x
}


