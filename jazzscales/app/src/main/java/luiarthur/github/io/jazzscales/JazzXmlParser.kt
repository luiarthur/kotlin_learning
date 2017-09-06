package luiarthur.github.io.jazzscales

import kotlin.text.Regex

// TODO: REFACTOR using JAXB (ships with Java).
//       see: http://techgarage.io/index.php/2017/01/07/jaxb-with-kotlin/
object JazzXmlParser {
    class Xml(val xml: String) {
        override fun toString(): String = xml
        val tag = Regex("<\\w+").find(xml)!!.value.replace("<", "")
        val name = Regex("name[^>]*>").find(xml)!!.value.dropLast(1).replace("name=","").replace("\"", "")
        val value = Regex(">[^<]*<").find(xml)!!.value.drop(1).dropLast(1)
    }

    fun getAllTags(xml: String, tag: String): List<String> {
        val pattern = "<$tag[^>]*>[^<]*<\\/$tag>"
        val res = Regex(pattern).findAll(xml)
        return res.map{it.value}.toList()
    }

    fun appendElement(xml: String, newElement:String):String {
        return xml.replace("</resources>", "    $newElement\n</resources>")
    }

    fun findElement(xml: String, elementName:String, tag:String): String {
        val candidates = JazzXmlParser.getAllTags(xml, tag)
        val c = candidates.map{JazzXmlParser.Xml(it)}
        val s = c.filter{it.name == elementName}.first().xml
        return s
    }

    fun removeElement(xml: String, elementName:String, tag:String):String {
        val candidates = JazzXmlParser.getAllTags(xml, tag)
        val c = candidates.map{JazzXmlParser.Xml(it)}
        val s:String = c.filter{it.name == elementName}.first().xml
        return xml.replace(s, "")
    }

    fun editElement(xml: String, elementName:String, tag:String, edit:String):String {
        val e = findElement(xml, elementName, tag)
        return xml.replace(Xml(e).value, edit)
    }
}  


/* Tests
:load JazzXmlParser.kt
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
s[1].tag
s[1].name
s[1].value
s.map{it.name}
s.filter{it.name == "Cdim6"}.first().value

JazzXmlParser.Xml(JazzXmlParser.findElement(testXml, "Cdim6", "scale")).value

val newElement = """<scale name="D">DDDDD</scale>"""
JazzXmlParser.appendElement(testXml, newElement)
JazzXmlParser.removeElement(testXml, "C", "scale")
JazzXmlParser.findElement(testXml, "C", "scale")
JazzXmlParser.editElement(testXml, "C", "scale", "ABC")
*/
