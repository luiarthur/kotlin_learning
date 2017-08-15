import Jazz

fun main(args: Array<String>) {
  println("Hi")
  println( "TEST: " + Jazz.Note("A#").isValid() )
  println( "TEST: " + !Jazz.Note("A5").isValid() )
}

