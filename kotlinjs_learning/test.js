function load(path) {
  var fs = require('fs');
  eval(fs.readFileSync(path).toString())
}

load('web/kotlin.js')
.load web/Jazz.js


Jazz.pianoNotes
Jazz.inPianoNotes("A")
