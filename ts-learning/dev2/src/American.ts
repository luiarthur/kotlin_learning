/// <reference path="Person.ts" />

class American implements Person {
  constructor(public firstname: string, public lastname: string) {}
  greet() {
    console.log(`Hello! I am ${this.firstname} ${this.lastname}.`)
  }
}
