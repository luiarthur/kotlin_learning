package luiarthur.github.io.jazzscales.Jazz

// Scale class
class Scale(override val notes: List<Note>, val key: Key) : ListNotes(notes) {
  override fun transpose(halfSteps: Int): Scale {
    return Scale(generalTranspose(halfSteps), key)
  }

  val increments = notes.indices.drop(1).map{ i -> notes[i].dist(notes[i-1]) }

  fun degree(deg: Degree): Note {
    val d = deg.d
    val a = deg.accidental
    require(1 <= d && d <= 8) {
      "The degree(d) must be between 1 and 8 (inclusive)."
    }

    val increment = when (a) {
      "#" ->     1
      "b" ->    -1 
      "","n" ->  0 
      else -> throw IllegalArgumentException("Only # and b are accepted as accidentals!")
    }

    val note = notes[d-1]
    return note.transpose(increment).toEnharmonic(note.letter)
  }


  fun blockChord(intervals: List<Int>): List<Chord> {
    require(intervals.all{1 <= it && it <= notes.size}) {
      "intervals have to be between 1 and ${notes.size}"
    }

    val scale = notes.dropLast(1)

    // changes parallel scales to scale of chords

    val parScale =  intervals.map{ i -> (scale.drop(i-1) + scale.take(i-1).map{it.transpose(12)} + scale.drop(i-1).first().transpose(12) )  }
    return parScale.flatten().withIndex().groupBy{ (it.index+1) % notes.size }.values.map{Chord(it.map{it.value})}
  }
}


