@JsName("pianoNotes")
val pianoNotes = listOf('A'..'G').flatten().map{it.toString()}.toTypedArray()

@JsName("inPianoNotes")
fun inPianoNotes(s: String): Boolean {
  return pianoNotes.contains(s)
}

@JsName("Bob")
class Bob(val x:Int, val y:Array<Int>) {
  override fun toString(): String {
    return "x: $x,  y: [$y]"
  }

  fun sum():Int = x + y.sum()
}

