import kotlin.test.*
import org.junit.Test

class TestSource {
    @Test fun `Stupid Test`() {
        assertEquals(1.toString() + "1", "11")
    }

    @Test fun `Stupid Test2`() {
      val x = 1
      assert(x == 1)
    }

    //@Test fun `This test should Fail`() {
    //  val x = 1
    //  assert(x == 2)
    //}
}
