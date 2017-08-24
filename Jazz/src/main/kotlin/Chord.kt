package Jazz

// Chord class
class Chord(override val notes: List<Note>, val key: Key?=null) : ListNotes(notes) { 
  override fun transpose(halfSteps: Int): Chord {
    return Chord(generalTranspose(halfSteps), key)
  }

  fun circle(direction: Int): Chord {
    return Chord(generalCircle(direction), key)
  }
}


