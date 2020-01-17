/// < reference path = "util.js" />
class Die {
  numFaces: number

  constructor(public faces: Array<string>) {
    this.numFaces = faces.length
  }

  roll(): string {
    let idx = util.getRandomInt(0, this.numFaces)
    return this.faces[idx]
  }
}

let die = new Die(['A'])
console.log(die)
