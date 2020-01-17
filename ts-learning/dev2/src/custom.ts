/// <reference path="Chinese" />
/// <reference path="American" />

function doubleMe(x: number) {
  return x * 2
}

module MyMod {
  let numberOne = 1

  export function addOne(a: number) {
    return a + numberOne
  }

  export let me = new Chinese('Arthur', 'Lui')
  export let myWife = new American('Makayla', 'Lui')
}
