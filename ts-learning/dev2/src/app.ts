/// <reference path="custom" />

class App {
  start() {
    MyMod.me.greet()
    MyMod.myWife.greet()
  }
}

let app = new App()
app.start()
