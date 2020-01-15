import * as M from "./file2";

let println = x => console.log(x)

function f(x: number) {
  let y = x + 1
  return y
}

function g(a: number) {
  return M.doubleMe(a)
}

function hello(name: string): void {
  println(name)
}
