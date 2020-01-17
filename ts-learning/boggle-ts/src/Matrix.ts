class Matrix<T> { // row major
  constructor(public vec: Array<Array<T>>) {
    for (let v of vec) {
      // assert(v.length == v[0].length)
    }
  }

  public nrow = this.vec.length
  public ncol = this.vec[0].length
  public nelem = this.nrow * this.ncol

}
