class Note(val note: String) {
  val letterNames = listOf('A'..'G').flatten()
  val accidentals = listOf("#", "b", "x", "d", "n", "")

  fun isValid(): Boolean {
    val letterName = note.first()
    val accidental = note.drop(1)
    
    return letterNames.contains(letterName) && accidentals.contains(accidental)
  }

}

/* Test class Note 
val note = Note("A#")
note.isValid()
Note("A6").isValid()
*/

data class Scales(val notes: List<String>, val key: String, )


arrayOf(1,2,3).contains(2)
