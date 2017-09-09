package luiarthur.github.io.jazzscales

import kotlin.text.Regex

data class JazzData(val type: String, val name: String, val list: List<String>, val music: String) {
    override fun toString(): String {
        return "type:$type; name:$name; list:${list.joinToString()}; music:{$music};"
    }
}

class JazzParser(val text: String) {
    val re = Regex("type\\s*:\\s*\\w+\\s*;\\s*name\\s*:\\s*\\w+\\s*;\\s*list\\s*:(\\s*\\w+\\s*,\\s*)*\\w+\\s*;\\s*music\\s*:\\s*\\{[^\\}]+\\};")

    private fun lineToJazzData(line: String): JazzData {
        val tnlm = line.split(";").map { it.split(":").last().trim() }
        return JazzData(tnlm[0], tnlm[1], tnlm[2].split(",").map { it.trim() }, tnlm[3].drop(1).dropLast(1))
    }

    val jazzData: List<JazzData> = run {
        val ls = re.findAll(text).map { it.value }.toList()
        ls.map { line -> lineToJazzData(line) }
    }

    override fun toString(): String {
        return jazzData.joinToString("\n")
    }

    fun contains(type: String, name: String): Boolean {
        return jazzData.map { Pair(it.type, it.name) }.contains(Pair(type, name))
    }

    fun getAllLists(): List<String> = jazzData.map { it.list }.flatten().toSet().filterNot { it == "none" }

    fun getAll(type: String): List<JazzData> {
        require(type in listOf("scale", "chord", "custom"))
        return jazzData.filter { it.type == type }
    }

    fun getElement(type: String, name: String): JazzData {
        return jazzData.filter { it.type == type && it.name == name }.first()
    }

    fun addElement(type: String, name: String, list: List<String>, music: String): JazzParser {
        require(type in listOf("scale", "chord", "custom"))
        require(!contains(type, name))
        return JazzParser(text + "\n" + JazzData(type, name, list, music).toString())
    }

    fun rmElement(type: String, name: String): JazzParser {
        val newText = jazzData.filterNot { it.type == type && it.name == name }.map { it.toString() }
        return JazzParser(newText.joinToString("\n"))
    }

    fun editElement(type: String, name: String, list: List<String>? = null, music: String? = null): JazzParser {
        val oldElement = getElement(type, name)
        return this.rmElement(type, name).addElement(type, name, list ?: oldElement.list, music ?: oldElement.music)
    }

    fun isValid(): Boolean {
        return jazzData.map { it.name + " " + it.type }.toSet().size == jazzData.size
    }

    fun rmFromList(type:String, name:String, list:String): JazzParser {
        val oldList = getElement(type, name).list
        val newList = oldList.filterNot { it == list }
        return editElement(type, name, if (newList.size == 0) listOf("none") else newList)
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

type:custom; name:custom2; list:none; music:{
  [C E] |
  FGA
};

### user-define items ###
"""

val p = JazzParser(text)
val j = p.jazzData
j.forEach{println(it)}

p.getAll("scale")
p.getAll("chord")
p.getAll("custom")

val p1 = p.addElement("scale", "bebop" , listOf("none"), "CDEFGA_BBc")
p1.addElement("scale", "bebop" , listOf("none"), "CDEFGA_BBc")
p1.contains("scale", "bebop")

p1.rmElement("scale", "bebop")
p1.getElement("scale", "bebop")
p1.editElement("scale", "bebop", list=listOf("hey", "now"), music="ABC")
*/
