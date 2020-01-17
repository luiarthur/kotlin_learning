class Pair {
  constructor(public x: number, public y: number) {}

  plus(that: Pair): Pair {
    return new Pair(this.x + that.x, this.y + that.y)
  }

  equals(that: Pair): boolean {
    return (this.x == that.x) && (this.y == that.y)
  }
}
