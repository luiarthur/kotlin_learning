/// < reference path = "util.js" />
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
var die = new Die(['A']);
console.log(die);
var Matrix = /** @class */ (function () {
    function Matrix(vec) {
        this.vec = vec;
        this.nrow = this.vec.length;
        this.ncol = this.vec[0].length;
        this.nelem = this.nrow * this.ncol;
        for (var _i = 0, vec_1 = vec; _i < vec_1.length; _i++) {
            var v = vec_1[_i];
            // assert(v.length == v[0].length)
        }
    }
    return Matrix;
}());
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
var util;
(function (util) {
    //The maximum is exclusive and the minimum is inclusive
    function getRandomInt(min, max) {
        min = Math.ceil(min);
        max = Math.floor(max);
        return Math.floor(Math.random() * (max - min)) + min;
    }
    util.getRandomInt = getRandomInt;
})(util || (util = {}));
