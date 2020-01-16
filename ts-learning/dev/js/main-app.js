define("file2", ["require", "exports"], function (require, exports) {
    "use strict";
    exports.__esModule = true;
    function doubleMe(x) {
        return x * 2;
    }
    exports.doubleMe = doubleMe;
});
define("file1", ["require", "exports", "file2"], function (require, exports, F2) {
    "use strict";
    exports.__esModule = true;
    exports.println = function (x) { return console.log(x); };
    function f(x) {
        var y = x + 1;
        return y;
    }
    exports.f = f;
    function g(a) {
        return F2.doubleMe(a);
    }
    function hello(name) {
        exports.println(name);
    }
});
define("main", ["require", "exports", "file1", "file2"], function (require, exports, F1, F2) {
    "use strict";
    exports.__esModule = true;
    function main() {
        var x = 1;
        F1.println("Hey there");
        console.log('boo');
        F1.println(F2.doubleMe(3));
        return x;
    }
    exports.main = main;
});
