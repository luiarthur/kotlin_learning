//import scala.scalajs.js.annotation._
//import scala.scalajs.js
//
//@JSExportTopLevel("Jazz")
//object Jazz{
//  @JSExport
//  val pianoNotes = 
//    js.Array("A", "B", "C", "D", "E", "F", "G")
//
//  @JSExport
//  def inPianoNotes(note: String):Boolean = {
//    pianoNotes.contains(note)
//  }
//
//}
//
//@ScalaJSDefined @JSExportTopLevel("Bob")
//class Bob(val x:Int, val y:js.Array[Int]) extends js.Object {
//  override def toString() = {
//    s"x: ${x.toString};  y: [${y.toString}]"
//  }
//
//  def sum() = y.sum + x
//}

import org.scalajs.dom
import dom.document

object TutorialApp {
  def appendPar(targetNode: dom.Node, text: String):Unit = {
    val parNode = document.createElement("p")
    val textNode = document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }

  def main(args: Array[String]): Unit = {
    appendPar(document.body, "Arthur was here!")
  }
}
