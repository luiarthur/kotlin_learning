package Jazz

// Key class
class Key(val key: String) {
  fun isValid(): Boolean {
    return Note(key).isValid()
  }
}


