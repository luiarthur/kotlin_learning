object Jazz {

  val letterNames = listOf('A'..'G').flatten()
  val octaves = listOf(1..7).flatten()
  val accidentals = listOf("#", "x", "b", "bb", "n", "")

  val lowestNote = "A0"
  val HighestNote = "C8"
  val sharpableNotes = listOf('C', 'D', 'F', 'G', 'A')

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

  // Note class
  class Note(val letter: Char, val octave: Int, val accidental: String="") {
    override fun toString(): String {
      return letter.toString() + octave + accidental
    }

    constructor(s: String) : this(s[0], s[1].toString().toInt(), s.drop(2))

    fun toEnharmonic(): Note? { // Note with sharp
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
        val eLetter = enharmonic[0]
        val eOctave = enharmonic[1].toString().toInt()
        val eAccidental = enharmonic.drop(2)

        Note(eLetter, eOctave, eAccidental)
      } else null

      return note
    }

    fun isValid(): Boolean {
      return when (this.toEnharmonic()) {
        null -> false
        else -> true
      }
    }


    fun transpose(halfSteps: Int): Note { 
      require(0 < halfSteps && halfSteps < 12) {
        "halfSteps must be between 0 and 12 (exclusive)."
      }
      return Note(pianoNotes[pianoNotes.indexOf(this.toEnharmonic().toString()) + halfSteps])
    }
  }

  // SeqNote Class
  abstract class ListNotes(open val notes: List<Note>) {
    fun isValid(): Boolean {
      return notes.map{it.isValid()}.all{ it }
    }

    fun transpose(halfSteps: Int): List<Note> {
      return notes.map{ it.transpose(halfSteps) }
    }

    fun circle(): List<String> {
      TODO()
    }
  }

  // Key class
  class Key(val key: String) {
    fun isValid(): Boolean {
      return Note(key).isValid()
    }
  }


  //// Chord class
  class Chord(override val notes: List<Note>, val key: Key) : ListNotes(notes) { 
  }

  // Scale class
  class Scale(override val notes: List<Note>, val key: Key) : ListNotes(notes) {
    fun blockChord(intervals: List<Int>): List<Chord> {
      TODO()
    }
  }
}

