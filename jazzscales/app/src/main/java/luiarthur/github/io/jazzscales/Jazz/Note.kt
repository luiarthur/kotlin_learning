package luiarthur.github.io.jazzscales.Jazz

// Note class
class Note(val letter: String, val octave: Int, val accidental: String="") {
  override fun toString(): String {
    return letter + octave + accidental
  }

  constructor(s: String) : this(s[0].toString(), s[1].toString().toInt(), s.drop(2))

  fun standardize(): Note? { // Note with sharp
    val halfSteps = when (accidental) {
      "#"  ->  1
      "x"  ->  2
      "b"  -> -1
      "bb" -> -2
      else ->  0 // "n" or ""
    }

    val currentIdx = pianoNotes.indexOf(letter.toString() + octave)
    val enharmonicIdx = currentIdx + halfSteps

    val note = if (currentIdx > -1 && 0 <= enharmonicIdx && enharmonicIdx < 88) {
      val enharmonic= pianoNotes[enharmonicIdx]
      val eLetter = enharmonic[0].toString()
      val eOctave = enharmonic[1].toString().toInt()
      val eAccidental = enharmonic.drop(2)

      Note(eLetter, eOctave, eAccidental)
    } else null

    return note
  }

  override operator fun equals(other: Any?): Boolean = when {
    other is Note -> this.standardize().toString() == other.standardize().toString()
    else -> false
  }

  fun toSharp(): Note? {
    TODO()
  }

  fun toFlat(): Note? {
    TODO()
  }

  fun toEnharmonic(e: String): Note { // FIXME
    // return enharmonic equivalent using `letter` in spelling
    val candidates = accidentals.map{ acc -> 
      val oct = when {
        e == "B" && letter == "C" -> octave - 1
        e == "C" && letter == "B" -> octave + 1
        else -> octave
      }
      Note(e + oct.toString() + acc) 
    }
    return candidates.filter{ it == this }.first()
  }

  fun isValid(): Boolean = when (this.standardize()) {
    null -> false
    else -> true
  }


  fun transpose(halfSteps: Int): Note { 
    require(-12 <= halfSteps && halfSteps <= 12) {
      "halfSteps must be between -12 and 12 (inclusive)."
    }
    val idx = pianoNotes.indexOf(this.standardize().toString()) + halfSteps
    return Note(pianoNotes[idx])
  }


  fun dist(that: Note): Int {
    return pianoNotes.indexOf(this.standardize().toString()) - pianoNotes.indexOf(that.standardize().toString())
  }
}
