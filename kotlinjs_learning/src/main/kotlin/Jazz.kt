@JsName("pianoNotes")
val pianoNotes = listOf('A'..'G').flatten().map{it.toString()}.toTypedArray()

@JsName("inPianoNotes")
fun inPianoNotes(s: String): Boolean {
  return pianoNotes.contains(s)
}

//@JsName("Bob")
//class Bob(val x:Int, val y:Array[Int]) {
//  fun sum() = x + y.sum()
//}
