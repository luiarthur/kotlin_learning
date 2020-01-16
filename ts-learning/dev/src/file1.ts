import * as F2 from "./file2";

export let println = (x: any) => console.log(x)

export function f(x: number) {
  let y = x + 1
  return y
}

function g(a: number) {
  return F2.doubleMe(a)
}

function hello(name: string): void {
  println(name)
}
