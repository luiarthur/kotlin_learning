define("util", ["require", "exports"], function (require, exports) {
    "use strict";
    exports.__esModule = true;
    //The maximum is exclusive and the minimum is inclusive
    function getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min)) + min;
    }
    exports.getRandomInt = getRandomInt;
});
define("Die", ["require", "exports", "util"], function (require, exports, util) {
    "use strict";
    exports.__esModule = true;
    var Die = /** @class */ (function () {
        function Die(faces) {
            this.faces = faces;
            this.numFaces = faces.length;
        }
        Die.prototype.roll = function () {
            var idx = util.getRandomInt(0, this.numFaces);
            return this.faces[idx];
        };
        return Die;
    }());
    exports.Die = Die;
    var die = new Die(['A']);
    console.log(die);
});
define("Pair", ["require", "exports"], function (require, exports) {
    "use strict";
    exports.__esModule = true;
    var Pair = /** @class */ (function () {
        function Pair(x, y) {
            this.x = x;
            this.y = y;
        }
        Pair.prototype.plus = function (that) {
            return new Pair(this.x + that.x, this.y + that.y);
        };
        Pair.prototype.equals = function (that) {
            return (this.x == that.x) && (this.y == that.y);
        };
        return Pair;
    }());
    exports.Pair = Pair;
});
