package luiarthur.github.io.jazzscales

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testXmlParser() {
        val testXml = """
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- predefined scales-->
    <scale name="Cdim6">CDEFG^GABc
    </scale>
    <scale name="C">CDEFGABc</scale>

    <!-- predefined chords-->
    <chord name="C">[EGBD]</chord>

    <!-- predefined custom patterns-->
    <custom name="custom1">[C E] D</custom>

    <!-- New Stuff -->
</resources>
"""

        val scales = JazzXmlParser.getAllTags(testXml, "scale")
        val s = scales.map{JazzXmlParser.Xml(it)}
        assertEquals(s[1].tag, "scale")
        assertEquals(s[1].name, "C")
        assertEquals(s[1].value, "CDEFGABc")

        val newElement = """<scale name="D">DDDDD</scale>"""
        val a = JazzXmlParser.appendElement(testXml, newElement)
        val b = JazzXmlParser.removeElement(testXml, "C", "scale")
        val c = JazzXmlParser.findElement(testXml, "C", "scale")
        val d = JazzXmlParser.editElement(testXml, "C", "scale", "ABC")
    }

    @Test
    fun testJazzParser() {
        val text = """
            ### predefined scales ###
            type:  scale; name: Cdim6; list: favorites  , routines; music: {
              CDEFG^GABc
            };
            type :scale; name:C; list:routines; music:{CDEFGABc};

            ### predefined chords ###
            type:chord; name:C; list:favorites; music:{[EGBD]};

            ### predefined custom patterns ###
            type:custom; name:custom1; list:none; music:{[C E] | D};

            type:custom; name:custom1; list:none; music:{
              [C E] |
              FGA
            };

            ### user-define items ###
        """

        val p = JazzParser(text)
        val j = p.jazzData()

        val p1 = p.addElement("scale", "bebop" , listOf("none"), "CDEFGA_BBc")
        assert(p1.contains("scale", "bebop"))
    }
}
