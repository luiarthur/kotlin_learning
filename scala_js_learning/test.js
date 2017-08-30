function load(path) {
  var fs = require('fs');
  eval(fs.readFileSync(path).toString())
}

load('target/scala-2.11/scala_js_learning-fastopt.js')

Jazz.pianoNotes
Jazz.inPianoNotes('A')
Jazz.inPianoNotes('A#')

var bob = new Bob(1, [1,2,3])
bob.sum()

bob.toString()
