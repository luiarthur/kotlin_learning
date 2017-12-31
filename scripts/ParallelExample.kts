fun <R>timer(block: () -> R):R {
  val t0 = System.nanoTime()
  val result = block()
  val t1 = System.nanoTime()
  println("Elapsed time: " + (t1 - t0) / 1E9 + "s")
  return result
}

fun heavyWork(time: Long=2000) {
  Thread.sleep(time)
}

val cores = args[0].toInt()

timer{
  (1..cores).toList().parallelStream().forEach{
    println("Doing heavyWork ${it}.")
    heavyWork()
  }
}

// val N = 100000
// val x = timer {Array(N, {it * 2.0 - 1})}

// .kts are kotlin script files.
// .kt are regular kotlin files.
// to run as script: 
// kotilnc -script ParallelExample.kts 8


