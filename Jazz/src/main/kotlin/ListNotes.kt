package Jazz

// ListNotes Class
abstract class ListNotes(open val notes: List<Note>) {
  override fun toString(): String {
    return notes.toString()
  }

  fun isValid(): Boolean {
    return notes.all{ it.isValid() }
  }

  protected fun generalTranspose(halfSteps: Int): List<Note> {
    return notes.map{ it.transpose(halfSteps) }
  }

  protected fun generalCircle(direction: Int=4): List<Note> {
    TODO()
  }

  abstract fun transpose(halfSteps: Int): Any
}



