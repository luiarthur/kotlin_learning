import kotlin.test.*
import org.junit.Test
import Jazz

class TestSource {
    @Test fun `Stupid Test`() {
        assertEquals(1.toString() + "1", "11")
    }

    @Test fun `Test Jazz Notes are Correct`() {
      assert(Jazz.pianoNotes[0] == "A0")
      assert(Jazz.pianoNotes[1] == "A0#")
      assert(Jazz.pianoNotes[2] == "B0")
      assert(Jazz.pianoNotes.last() == "C8")
      assert(Jazz.pianoNotes[3] == "C1")
      assert(Jazz.pianoNotes.size == 88)
    }

    @Test fun `Test Jazz`() {
      println("Hi")
      assert( Jazz.Note("A3#").isValid() )
      assert( Jazz.Note("A3#").toString() == "A3#" )
      assert( !Jazz.Note("A9").isValid() )
    }

}
