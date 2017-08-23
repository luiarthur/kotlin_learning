object Jazz {

  val letterNames = listOf('A'..'G').flatten().map{it.toString()}
  val octaves = listOf(1..7).flatten()
  val accidentals = listOf("#", "x", "b", "bb", "n", "")

  val lowestNote = "A0"
  val HighestNote = "C8"
  val sharpableNotes = listOf("C", "D", "F", "G", "A")

  val pianoNotes = listOf(
      "A0", "A0#", "B0", 
      "C1", "C1#", "D1", "D1#", "E1", "F1", "F1#", "G1", "G1#", "A1", "A1#", "B1",
      "C2", "C2#", "D2", "D2#", "E2", "F2", "F2#", "G2", "G2#", "A2", "A2#", "B2",
      "C3", "C3#", "D3", "D3#", "E3", "F3", "F3#", "G3", "G3#", "A3", "A3#", "B3",
      "C4", "C4#", "D4", "D4#", "E4", "F4", "F4#", "G4", "G4#", "A4", "A4#", "B4",
      "C5", "C5#", "D5", "D5#", "E5", "F5", "F5#", "G5", "G5#", "A5", "A5#", "B5",
      "C6", "C6#", "D6", "D6#", "E6", "F6", "F6#", "G6", "G6#", "A6", "A6#", "B6",
      "C7", "C7#", "D7", "D7#", "E7", "F7", "F7#", "G7", "G7#", "A7", "A7#", "B7",
      "C8")

  // Resolve accidentals
  fun resolve(s: String): String {
    TODO()
  }

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

    override operator fun equals(that: Any?): Boolean = when {
      that is Note -> this.standardize().toString() == that.standardize().toString()
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
          e == "B" && acc != "#" && acc != "x"  && letter == "C" -> octave - 1
          e == "C" && acc != "b" && acc != "bb" && letter == "B" -> octave + 1
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
      return Note(pianoNotes[pianoNotes.indexOf(this.standardize().toString()) + halfSteps])
    }
  }

  // ListNotes Class
  abstract class ListNotes(open val notes: List<Note>) {
    override fun toString(): String {
      return notes.toString()
    }

    fun isValid(): Boolean {
      return notes.all{ it.isValid() }
    }

    protected fun generalTranspose(halfSteps: Int): List<Note> { // reimplement in children
      return notes.map{ it.transpose(halfSteps) }
    }

    protected fun generalCircle(direction: Int=4): List<Note> { // reimplement in children
      TODO()
    }

    abstract fun transpose(halfSteps: Int): Any
  }


  // Key class
  class Key(val key: String) {
    fun isValid(): Boolean {
      return Note(key).isValid()
    }
  }


  //// Chord class
  class Chord(override val notes: List<Note>, val key: Key) : ListNotes(notes) { 
    override fun transpose(halfSteps: Int): Chord {
      return Chord(generalTranspose(halfSteps), key)
    }

    fun circle(direction: Int): Chord {
      return Chord(generalCircle(direction), key)
    }
  }

  // Scale class
  class Scale(override val notes: List<Note>, val key: Key) : ListNotes(notes) {
    override fun transpose(halfSteps: Int): Scale {
      return Scale(generalTranspose(halfSteps), key)
    }

    fun blockChord(intervals: List<Int>): List<Chord> {
      TODO()
    }
  }

  // Generate Inonian scale
  fun ionian(startNote: Note):Scale { // FIXME
    val increments = listOf(2,2,1,2,2,2,1) // W, W, H, W, W, W, H

    val idx = letterNames.indexOf(startNote.letter)
    val letters = letterNames.drop(idx) + letterNames.take(idx) + startNote.letter
    println(letters)

    val scale = Array(8) { startNote }
    println(scale[0])
    for (i in scale.indices) {
      when (i) {
        0 -> scale[i] = startNote
        else -> {
          scale[i] = scale[i-1].transpose(increments[i-1]).toEnharmonic(letters[i])
          println(scale[i])
        }
      }
    }

    //val scaleWithAccidentals = scale.toList().zip(letters){(s,l) -> s.toEnharmonic(l)}
    //return Scale(scaleWithAccidentals, key=Key(startNote.letter))
    return Scale(scale.toList(), key=Key(startNote.letter))
  }
}

