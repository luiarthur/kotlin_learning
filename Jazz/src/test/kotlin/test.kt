import kotlin.test.*
import org.junit.Test
import Jazz

class TestSource {
    @Test fun `Stupid Test`() {
        assertEquals(1.toString() + "1", "11")
    }

    @Test fun `Test Jazz Piano Notes are Correct`() {
      assert(Jazz.pianoNotes[0] == "A0")
      assert(Jazz.pianoNotes[1] == "A0#")
      assert(Jazz.pianoNotes[2] == "B0")
      assert(Jazz.pianoNotes.last() == "C8")
      assert(Jazz.pianoNotes[3] == "C1")
      assert(Jazz.pianoNotes.size == 88)
    }

    @Test fun `Test Jazz Note`() {
      assert( Jazz.Note('A', 3, "#").isValid() )
      assert( Jazz.Note('A', 3, "#").toString() == "A3#" )
      assert( Jazz.Note('B', 3, "b").isValid() )
      assert( Jazz.Note('B', 3, "b").toEnharmonic().toString() == "A3#" )
      assert( Jazz.Note('B', 3, "b").toEnharmonic().toString() == 
              Jazz.Note('A', 3, "#").toEnharmonic().toString())
      assert( !Jazz.Note('C', 8, "#").isValid() )
      assert( !Jazz.Note('A', 9).isValid() )
      assert( !Jazz.Note('F', 8, "#").isValid() )
      assert( Jazz.Note('A', 0, "#").isValid() )
      assert( !Jazz.Note('A', 0, "b").isValid() )
      assert( !Jazz.Note('G', 0, "x").isValid() )
      assert( !Jazz.Note('D', 8, "bb").isValid() )
      assert( !Jazz.Note("D8bb").isValid() )
      assert( Jazz.Note("A3#").isValid() )
    }

    @Test fun `Test Chord`() {
      val chord = Jazz.Chord(listOf(Jazz.Note("A3"), Jazz.Note("C4#"), Jazz.Note("G4"), Jazz.Note("C5")), Jazz.Key("Am") )
      println(chord.notes)
      println(chord.transpose(2).notes)
      println(chord.transpose(1).notes)
      println(chord.transpose(-2).notes)
    }

    @Test fun `Test Scale`() {
      val scale = Jazz.Scale(listOf(Jazz.Note("A3"), Jazz.Note("B3"), Jazz.Note("C4"), Jazz.Note("D5"), Jazz.Note("E5")), Jazz.Key("Am") )
      println(scale.notes)
      println(scale.transpose(1).notes)
      println(scale.transpose(2).notes)
      println(scale.transpose(-2).notes)
    }
}
