package luiarthur.github.io.jazzscales

import kotlin.text.Regex

object JazzXmlParser {
  class Xml(val xml: String) {
    override fun toString(): String = xml
    val tag = Regex("<\\w+").find(xml)?.value?.replace("<", "")
    val name = Regex("name[^>]*>").find(xml)?.value?.dropLast(1)?.replace("name=","")
    val value = Regex(">[^<]*<").find(xml)?.value?.drop(1)?.dropLast(1)
  }

  fun getAllTags(xml: String, tag: String): List<String> {
    val pattern = "<$tag[^>]*>[^<]*<\\/$tag>"
    val res = Regex(pattern).findAll(xml)
    return res.map{it.value}.toList()
  }
}  


// Tests
//  val testXml = """
//  <?xml version="1.0" encoding="utf-8"?>
//  <resources>
//      <!-- predefined scales-->
//      <scale name="Cdim6">CDEFG^GABc
//      </scale>
//      <scale name="C">CDEFGABc</scale>
//
//      <!-- predefined chords-->
//      <chord name="C">[EGBD]</chord>
//
//      <!-- predefined custom patterns-->
//      <custom name="custom1">[C E] D</custom>
//  </resources>
//  """

// val scales = getAllTags(testXml, "scale")
// val s = scales.map{Xml(it)}
// s[1].tag
// s[1].name
// s[1].value
