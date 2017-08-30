function load(path) {
  var fs = require('fs');
  eval(fs.readFileSync(path).toString())
}

load('target/scala-2.11/scala_js_learning-fastopt.js')

jazz.pianoNotes
jazz.inPianoNotes('A')
jazz.inPianoNotes('A#')
