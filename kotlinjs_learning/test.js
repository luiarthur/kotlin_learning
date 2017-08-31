function load(path) {
  var fs = require('fs');
  eval(fs.readFileSync(path).toString())
}

load('web/kotlin.js')
.load web/Jazz.js


Jazz.pianoNotes
assert(Jazz.inPianoNotes("A"))
assert(!Jazz.inPianoNotes("#A"))

var bob = new Jazz.Bob(1, [1,2,3])
assert(bob.sum() == 7)

bob.toString()
