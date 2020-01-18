/// < reference path = "util.js" />
class Die {
    constructor(faces) {
        this.faces = faces;
        this.numFaces = faces.length;
    }
    roll() {
        let idx = util.getRandomInt(0, this.numFaces);
        return this.faces[idx];
    }
}
// TODO: remove these
let die = new Die(['A']);
console.log(die);
// import * as React from 'react';
// import { render } from 'react-dom';
class Counter extends react.Component {
    constructor() {
        super(...arguments);
        this.state = {
            count: 0
        };
        this.increment = () => {
            this.setState({
                count: (this.state.count + 1)
            });
        };
        this.decrement = () => {
            this.setState({
                count: (this.state.count - 1)
            });
        };
    }
    render() {
        return (react.createElement("div", null,
            react.createElement("h1", null, this.state.count),
            react.createElement("button", { onClick: this.increment }, "Increment"),
            React.createElement("button", { onClick: this.decrement }, "Decrement")));
    }
}
// render(<Counter/>, document.getElementById('root'));
React.createElement(Counter, null);
class Matrix {
    constructor(vec) {
        this.vec = vec;
        this.nrow = this.vec.length;
        this.ncol = this.vec[0].length;
        this.nelem = this.nrow * this.ncol;
        for (let v of vec) {
            // assert(v.length == v[0].length)
        }
    }
}
class Pair {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
    plus(that) {
        return new Pair(this.x + that.x, this.y + that.y);
    }
    equals(that) {
        return (this.x == that.x) && (this.y == that.y);
    }
}
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
