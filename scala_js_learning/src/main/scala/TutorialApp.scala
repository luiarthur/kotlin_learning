import scala.scalajs.js.JSApp

object TutorialApp extends JSApp {
    val pianoNotes = List("A", "B", "C", "D", "E", "F", "G")

    def inPianoNotes(note: String) {
      pianoNotes.contains(note)
    }


  def main() {
    println("My first scala.js app")
  }
}
