package luiarthur.github.io.jazzscales

import kotlin.text.Regex

data class JazzData(
    val type: String, 
    val name: String, 
    val list: List<String>,
    val music: String) {
  override fun toString(): String {
    return "type:$type; name:$name; list:${list.joinToString()}, music:{$music};"
  }
}

class JazzParser(val text: String) {
  override fun toString(): String {
    return text
  }

  val re = Regex("type\\s*:\\s*\\w+\\s*;\\s*name\\s*:\\s*\\w+\\s*;\\s*list\\s*:(\\s*\\w+\\s*,\\s*)*\\w+\\s*;\\s*music\\s*:\\s*\\{[^\\}]+\\};")

  private fun lineToJazzData(line: String): JazzData {
    val tnlm = line.split(";").map{it.split(":").last().trim()}
    return JazzData(tnlm[0], tnlm[1], tnlm[2].split(",").map{it.trim()}, tnlm[3].drop(1).dropLast(1))
  }

  fun toJazzData(): List<JazzData> {
    val ls = re.findAll(text).map{it.value}.toList()
    return ls.map{line -> lineToJazzData(line)}
  }

  val jazzData = this.toJazzData()

  fun getAllLists(): List<String> = jazzData.map{it.list}.flatten().toSet().filterNot{it == "none"}

  fun getAll(type: String): List<JazzData> {
    require(type in listOf("scale","chord","custom"))
    return jazzData.filter{ it.type == type }
  }

  fun addElement(type:String, name: String, list: List<String>, music: String): JazzParser {
    require(type in listOf("scale","chord","custom"))
    return JazzParser(text + "\n" + JazzData(type,name,list,music).toString())
  }

  fun rmElement(name: String, music: String, list: String): JazzParser {
    TODO()
  }

  fun editElement(name: String, music: String, list: String): JazzParser {
    TODO()
  }
}

/* Tests
:load JazzParser.kt
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
val j = p.toJazzData()
j.forEach{println(it)}

p.getAll("scale")
p.getAll("chord")
p.getAll("custom")

val p1 = p.addElement("scale", "bebop" , listOf("none"), "CDEFGA_BBc")
p1.addElement("scale", "bebop" , listOf("none"), "CDEFGA_BBc")

*/
