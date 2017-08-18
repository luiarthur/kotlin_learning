import kotlin.test.*
import org.junit.Test
import Jazz

class TestSource {
    @Test fun `Stupid Test`() {
        assertEquals(1.toString() + "1", "11")
    }


  @Test fun `Test Jazz`() {
    println("Hi")
    assert( Jazz.Note("A#").isValid() )
    assert( Jazz.Note("A#").toString() == "A#" )
    assert( !Jazz.Note("A5").isValid() )
  }

}
