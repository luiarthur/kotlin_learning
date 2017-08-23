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
      assert( Jazz.Note("A", 3, "#").isValid() )
      assert( Jazz.Note("A", 3, "#").toString() == "A3#" )
      assert( Jazz.Note("B", 3, "b").isValid() )
      assert( Jazz.Note("B", 3, "b").standardize().toString() == "A3#" )
      assert( Jazz.Note("B", 3, "b").standardize().toString() == 
              Jazz.Note("A", 3, "#").standardize().toString())
      assert( !Jazz.Note("C", 8, "#").isValid() )
      assert( !Jazz.Note("A", 9).isValid() )
      assert( !Jazz.Note("F", 8, "#").isValid() )
      assert( Jazz.Note("A", 0, "#").isValid() )
      assert( !Jazz.Note("A", 0, "b").isValid() )
      assert( !Jazz.Note("G", 0, "x").isValid() )
      assert( !Jazz.Note("D", 8, "bb").isValid() )
      assert( !Jazz.Note("D8bb").isValid() )
      assert( Jazz.Note("A3#").isValid() )
      assert( Jazz.Note("A3#") == Jazz.Note("B3b") )
      assert( Jazz.Note("A3#") != Jazz.Note("C3b") )
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

    @Test fun `Test Note Enharmonic`() {
      assert(Jazz.Note("A4#") == Jazz.Note("B4b"))
      assert(Jazz.Note("B4") == Jazz.Note("C5b"))
      assert(Jazz.Note("B4#") == Jazz.Note("C5"))
      assert(Jazz.Note("A4#").toEnharmonic("B") == Jazz.Note("B4b"))
      assert(Jazz.Note("G4#").toEnharmonic("A") == Jazz.Note("A4b"))
      assert(Jazz.Note("B4x").toEnharmonic("C") == Jazz.Note("C5#"))
      assert(Jazz.Note("B3#").toEnharmonic("C") == Jazz.Note("C4"))
      assert(Jazz.Note("B3#").toEnharmonic("C") == Jazz.Note("C4n"))
      assert(Jazz.Note("C3b").toEnharmonic("B") == Jazz.Note("B2"))
      assert(Jazz.Note("G6b").toEnharmonic("F") == Jazz.Note("F6#"))
      assert(Jazz.Note("A6b").toEnharmonic("G") == Jazz.Note("G6#"))
      assert(Jazz.Note("A6b").toEnharmonic("A") == Jazz.Note("A6b"))
      assert(Jazz.Note("C6").toEnharmonic("C") == Jazz.Note("C6"))
      assert(Jazz.Note("C6b").toEnharmonic("C") == Jazz.Note("C6b"))
      assert(Jazz.Note("B6b").toEnharmonic("B") == Jazz.Note("B6b"))
      assert(Jazz.Note("B6#").toEnharmonic("B") == Jazz.Note("B6#"))
      assert(Jazz.Note("C6#").toEnharmonic("C") == Jazz.Note("C6#"))
      assert(Jazz.Note("C6#").toEnharmonic("D") == Jazz.Note("D6b"))
    }

    @Test fun `Test Ionian Generator`() {
      println(Jazz.ionian( Jazz.Note("A4") ))
      println(Jazz.ionian( Jazz.Note("F4") ))
      println(Jazz.ionian( Jazz.Note("F4#") ))
      println(Jazz.ionian( Jazz.Note("B4b") ))
      println(Jazz.ionian( Jazz.Note("E4b") ))
      println(Jazz.ionian( Jazz.Note("C4#") ))
      println(Jazz.ionian( Jazz.Note("D4#") ))
    }
}
