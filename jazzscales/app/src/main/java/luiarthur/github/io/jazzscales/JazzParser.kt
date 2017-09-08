package luiarthur.github.io.jazzscales

import kotlin.text.Regex

data class JazzData(type: String, name: String, music: String, list: String="none")


/* Tests
:load JazzParser.kt
val text = """
### predefined scales ###
type:scale; name:Cdim6; music:CDEFG^GABc; list:favorites,routines;
type:scale; name:C; music:CDEFGABc; list:routines;

### predefined chords ###
type:chord; name:C; music:[EGBD]; list:favorites;

### predefined custom patterns ###
type:custom; name:custom1; music:[C E] D; list:none;

type:custom;
name:custom1;
music:[C E]
FGA;
list:none;

### user-define items ###
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
