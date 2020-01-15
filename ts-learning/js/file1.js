"use strict";
exports.__esModule = true;
var M = require("./file2");
var println = function (x) { return console.log(x); };
function f(x) {
    var y = x + 1;
    return y;
}
function g(a) {
    return M.doubleMe(a);
}
function hello(name) {
    println(name);
}
