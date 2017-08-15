object Jazz {
  // Note class
  class Note(val note: String) {
    val letterNames = listOf('A'..'G').flatten()
    val accidentals = listOf("#", "b", "x", "d", "n", "")

    fun isValid(): Boolean {
      val letterName = note.first()
      val accidental = note.drop(1)
      
      return letterNames.contains(letterName) && accidentals.contains(accidental)
    }

    fun transpose(halfSteps: Int) { 
      TODO() 
    }
  }


  // Key class
  class Key(val key: String) {
    fun isValid(): Boolean {
      return Note(key).isValid()
    }
  }


  // Scale class
  class Scale(val notes: List<Note>, key: Key) {
    fun isValid(): Boolean {
      return notes.map{it.isValid()}.all{ it }
    }
    
    fun transpose(halfSteps: Int) {
      TODO()
    }
  }
}

// Test class Note 
//val note = Note("A#")
//note.isValid()
//Note("A6").isValid()

//data class Scales(val notes: List<String>, val key: String, )


//arrayOf(1,2,3).contains(2)

/*
:load jazz.kt
 */
