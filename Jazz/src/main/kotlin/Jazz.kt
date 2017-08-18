object Jazz {
  // Note class
  class Note(val note: String) {
    override fun toString(): String { return note }

    val letterNames = listOf('A'..'G').flatten()
    val accidentals = listOf("#", "b", "x", "d", "n", "")

    fun isValid(): Boolean {
      val letterName = note.first()
      val accidental = note.drop(1)
      
      return letterNames.contains(letterName) && accidentals.contains(accidental)
    }

    fun transpose(halfSteps: Int): Note { 
      require(0 < halfSteps && halfSteps < 12) {
        "halfSteps must be between 0 and 12 (exclusive)."
      }
      TODO() 
    }
  }


  // Key class
  class Key(val key: String) {
    fun isValid(): Boolean {
      return Note(key).isValid()
    }
  }


  // Chord class
  class Chord(val notes: List<Note>, key: Key) {
    fun isValid(): Boolean {
      return notes.map{it.isValid()}.all{ it }
    }

    fun transpose(halfSteps: Int): Scale {
      TODO()
    }

    fun circle(): List<Chord> {
      TODO()
    }
  }

  // Scale class
  class Scale(val notes: List<Note>, key: Key) {
    fun isValid(): Boolean {
      return notes.map{it.isValid()}.all{ it }
    }
    
    fun transpose(halfSteps: Int): Scale {
      TODO()
    }

    fun circle(): List<Scale> {
      TODO()
    }

    fun blockChord(intervals: List<Int>): List<Chord> {
      TODO()
    }
  }

}

