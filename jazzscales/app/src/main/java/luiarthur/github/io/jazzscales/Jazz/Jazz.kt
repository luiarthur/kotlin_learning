package luiarthur.github.io.jazzscales.Jazz

val letterNames = listOf('A'..'G').flatten().map{it.toString()}
val octaves = listOf(1..7).flatten()
val accidentals = listOf("#", "b", "x", "bb", "", "n")

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

// Generate Inonian scale
fun ionian(startNote: Note):Scale {
  val increments = listOf(2,2,1,2,2,2,1) // W, W, H, W, W, W, H

  val idx = letterNames.indexOf(startNote.letter)
  val letters = letterNames.drop(idx) + letterNames.take(idx) + startNote.letter

  val scale = Array(8) { startNote }
  for (i in scale.indices) {
    when (i) {
      0 -> scale[i] = startNote
      else -> {
        scale[i] = scale[i-1].transpose(increments[i-1]).toEnharmonic(letters[i])
      }
    }
  }

  return Scale(scale.toList(), key=Key(startNote.letter))
}

// Given a list of degrees (eg. listOf(1,2,3,4,5,5#,6,7,8), return the scale
fun flexScale(degrees:List<Degree>, startNote:Note, baseScale:(Note)->Scale={ionian(it)}): Scale {
  val bs = baseScale(startNote)
  return Scale(degrees.map{ bs.degree(it) }, Key(startNote.letter+startNote.accidental))
}


