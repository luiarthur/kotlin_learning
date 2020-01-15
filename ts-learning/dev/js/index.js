define("file2", ["require", "exports"], function (require, exports) {
    "use strict";
    exports.__esModule = true;
    function doubleMe(x) {
        return x * 2;
    }
    exports.doubleMe = doubleMe;
});
define("file1", ["require", "exports", "file2"], function (require, exports, M) {
    "use strict";
    exports.__esModule = true;
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
});
