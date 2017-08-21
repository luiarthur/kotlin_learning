object Jazz {

  val letterNames = listOf('A'..'G').flatten()
  val octaves = listOf(1..7).flatten()
  val accidentals = listOf("#", "b", "x", "d", "n", "")
  val lowestNote = "A0"
  val HighestNote = "C8"
  val sharpableNotes = listOf('C', 'D', 'F', 'G', 'A')

  // FIXME
  fun generateNotes(pianoNotes: List<String> = listOf(lowestNote, "A0#")): List<String> {
    val currentNote = pianoNotes.last()
    val currentLetter = currentNote[0]
    val currentOctave = currentNote[1].toString().toInt()
    val currentSharped = currentNote.drop(2) == "#"
    val currentLetterIdx = currentNote.indexOf(currentLetter)

    if (currentNote == HighestNote) {
      return pianoNotes
    } else {
      val nextLetter = letterNames[if (currentLetterIdx + 1 == letterNames.size) 0 else currentLetterIdx + 1]
      val nextOctave = (if (currentLetter == 'B') currentOctave + 1 else currentOctave).toString()
      val nextAccidental = if (!currentSharped && sharpableNotes.contains(currentLetter)) "#" else ""

      val nextNote = nextLetter + nextOctave + nextAccidental
      return pianoNotes + nextNote
    }
  }

  val pianoNotes = generateNotes()

  // Note class
  class Note(val note: String) {
    override fun toString(): String { return note }

    fun isEnharmonic(): Boolean {
      TODO()
    }

    fun isValid(): Boolean {
      val letterName = note[0]
      val octave = note[1].toString().toInt()
      val accidental = note.drop(2)

      val valid = letterNames.contains(letterName) &&
                  accidentals.contains(accidental) &&
                  octaves.contains(octave)

      return valid
    }

    fun transpose(halfSteps: Int): Note { 
      require(0 < halfSteps && halfSteps < 12) {
        "halfSteps must be between 0 and 12 (exclusive)."
      }
      TODO() 
    }
  }

  // SeqNote Class
  abstract class ListNotes(open val notes: List<Note>) {
    fun isValid(): Boolean {
      return notes.map{it.isValid()}.all{ it }
    }

    fun transpose(halfSteps: Int): List<Note> {
      return notes.map{ Note(it.transpose(halfSteps).note) }
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


  // Chord class
  class Chord(override val notes: List<Note>, val key: Key) : ListNotes(notes) { 
  }

  // Scale class
  class Scale(override val notes: List<Note>, val key: Key) : ListNotes(notes) {
    fun blockChord(intervals: List<Int>): List<Chord> {
      TODO()
    }
  }

}

