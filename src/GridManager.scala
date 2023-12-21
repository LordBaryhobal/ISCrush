class GridManager(size : Int) {
  var grid: Array[Array[Candy]] = Array.ofDim(size, size)
  var tmpGrid: Array[Array[Candy]] = Array.ofDim(size, size)
  generateRandomGrid()

  def generateRandomGrid(): Unit = {
    for (i: Int <- 0 until size) {
      for (j: Int <- 0 until size) {
        grid(i)(j) = randomCandy()
      }
    }
  }

  /**
   * Displays the grid of candies in the console
   */
  def displayGrid(): Unit = {
    for (i: Int <- 0 until size) {
      for (j: Int <- 0 until size) {
        print(grid(i)(j)+ " ")
      }
      println()
    }
  }

  def randomCandy(): Candy = {
    val randomLetter = util.Random.nextInt(Candy.models.length)
    val candy : Candy = new Candy(Candy.models(randomLetter))
    return candy
  }

  def processCombos(): Boolean = {
    copyGrid()
    var hasChanged: Boolean = false

    for (y: Int <- 0 until size) {
      var count: Int = 0
      var symbol: Char = 0
      for (x: Int <- 0 until size) {
        var candy: Candy = grid(y)(x)
        if (candy.symbol == symbol) {
          count += 1
        } else {
          if (count >= 3) {
            simplifyCombo(x - count, y, x-1, y)
            hasChanged = true
          }
          count = 1
          symbol = candy.symbol
        }
      }
      if (count >= 3) {
        simplifyCombo(size - count, y, size-1, y)
        hasChanged = true
      }
    }

    for (x: Int <- 0 until size) {
      var count: Int = 0
      var symbol: Char = 0
      for (y: Int <- 0 until size) {
        var candy: Candy = grid(y)(x)
        if (candy.symbol == symbol) {
          count += 1
        } else {
          if (count >= 3) {
            simplifyCombo(x, y - count, x, y - 1)
            hasChanged = true
          }
          count = 1
          symbol = candy.symbol
        }
      }
      if (count >= 3) {
        simplifyCombo(x, size - count, x, size - 1)
        hasChanged = true
      }
    }

    grid = tmpGrid

    return hasChanged
  }

  def simplifyCombo(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    for (y: Int <- y1 to y2) {
      for (x: Int <- x1 to x2) {
        tmpGrid(y)(x) = Candy.empty()
      }
    }
  }

  def copyGrid(): Unit = {
    tmpGrid = Array.ofDim(size, size)

    for (i: Int <- grid.indices) {
      for (j: Int <- grid(i).indices) {
        tmpGrid(i)(j) = grid(i)(j)
      }
    }
  }
}

object GridManager extends App {
  Candy.init()
  var gridOne : GridManager = new GridManager(12)
  gridOne.displayGrid()
  val simplified: Boolean = gridOne.processCombos()
  println(s"Combos ? $simplified")
  gridOne.displayGrid()
}

