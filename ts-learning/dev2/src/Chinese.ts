/// <reference path="Person" />

class Chinese implements Person {
  constructor(public firstname: string, public lastname: string) {}
  greet() {
    console.log(`Hello! I am ${this.lastname} ${this.firstname}.`)
  }
}
