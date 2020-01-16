import * as F2 from "./file2";
export var println = function (x) { return console.log(x); };
export function f(x) {
    var y = x + 1;
    return y;
}
function g(a) {
    return F2.doubleMe(a);
}
function hello(name) {
    println(name);
}
