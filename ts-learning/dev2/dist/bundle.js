/// <reference path="Person.ts" />
var American = /** @class */ (function () {
    function American(firstname, lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
    American.prototype.greet = function () {
        console.log("Hello! I am " + this.firstname + " " + this.lastname + ".");
    };
    return American;
}());
/// <reference path="Person" />
var Chinese = /** @class */ (function () {
    function Chinese(firstname, lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }
    Chinese.prototype.greet = function () {
        console.log("Hello! I am " + this.lastname + " " + this.firstname + ".");
    };
    return Chinese;
}());
/// <reference path="Chinese" />
/// <reference path="American" />
function doubleMe(x) {
    return x * 2;
}
var MyMod;
(function (MyMod) {
    var numberOne = 1;
    function addOne(a) {
        return a + numberOne;
    }
    MyMod.addOne = addOne;
    MyMod.me = new Chinese('Arthur', 'Lui');
    MyMod.myWife = new American('Makayla', 'Lui');
})(MyMod || (MyMod = {}));
/// <reference path="custom" />
var App = /** @class */ (function () {
    function App() {
    }
    App.prototype.start = function () {
        MyMod.me.greet();
        MyMod.myWife.greet();
    };
    return App;
}());
var app = new App();
app.start();
