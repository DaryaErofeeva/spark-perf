package spark.perf

import java.util.Random
import java.util.logging.{Level, LogManager}

/**
  * Created by giovanniquattrocchi on 26/06/17.
  */

class ZipfRandom(val size: Int, val skew: Int, val seed: Int) {
  val log = LogManager.getLogManager.getLogger("ZipfRandom")
  log.setLevel(Level.ALL)
  log.info("parameters = "+size + " " + skew + " " + seed)
  val rnd = new Random(seed)
  val harmonic: Double = (1 to size).foldLeft(0d)((a, b) => a + (1.0d / Math.pow(b, skew)))

  def nextInt() : Int = {

    var rank: Int = 0
    var p, dice: Double = 0

    do {
      rank = rnd.nextInt(size) + 1
      p = getProbability(rank)
      dice = rnd.nextDouble()
      log.info("dice "+dice+" p "+p)
    }
    while (dice >= p)
    log.info("returning rank "+rank)
    rank
  }


  def getProbability(rank: Int): Double = {
    (1.0d / Math.pow (rank, skew) ) / harmonic
  }

}