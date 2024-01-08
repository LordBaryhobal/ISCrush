class GridManager(val size : Int) {
  var grid: Array[Array[Candy]] = Array.ofDim(size, size)
  var tmpGrid: Array[Array[Candy]] = Array.ofDim(size, size)
  generateRandomGrid()

  /**
   * Fills the grid with random candies
   */
  def generateRandomGrid(): Unit = {
    for (i: Int <- 0 until size) {
      for (j: Int <- 0 until size) {
        grid(i)(j) = randomCandy()
      }
    }
    simplifyGrid()
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

  /**
   * Generates a random candy
   *
   * @return The random candy
   */
  def randomCandy(): Candy = {
    val randomLetter = util.Random.nextInt(Candy.models.length)
    val candy : Candy = new Candy(Candy.models(randomLetter))
    return candy
  }

  /**
   * Scans the whole grid and simplifies combos
   *
   * @return true if at least one combo was found, false otherwise
   */
  private def processCombos(): Boolean = {
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

  /**
   * Simplify one combo and replaces it with empty candies
   *
   * @param x1 X position of top left corner
   * @param y1 Y position of top left corner
   * @param x2 X position of bottom right corner
   * @param y2 Y position of bottom right corner
   */
  private def simplifyCombo(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    for (y: Int <- y1 to y2) {
      for (x: Int <- x1 to x2) {
        tmpGrid(y)(x) = Candy.empty()
      }
    }
  }

  /**
   * Makes a copy of the grid in `tmpGrid`
   */
  private def copyGrid(): Unit = {
    tmpGrid = Array.ofDim(size, size)

    for (i: Int <- grid.indices) {
      for (j: Int <- grid(i).indices) {
        tmpGrid(i)(j) = grid(i)(j)
      }
    }
  }

  /**
   * Moves down columns with holes
   *
   * @return true if something was moved, false otherwise
   */
  private def moveDown(): Boolean = {
    var moved: Boolean = false

    for (y: Int <- size - 1 to 0 by -1) {
      for (x: Int <- 0 until size) {
        if (grid(y)(x).isEmpty()) {
          moved = true
          for (y2: Int <- y until 0 by -1) {
            grid(y2)(x) = grid(y2-1)(x)
          }
          grid(0)(x) = randomCandy()
        }
      }
    }
    return moved
  }

  /**
   * Moves down all columns with hole until the grid is full
   */
  private def moveDownUntilFull(): Unit = {
    var moved: Boolean = true

    while (moved) {
      moved = moveDown()
    }
  }

  /**
   * Simplifies all combos and moves down columns until there is no combo
   */
  def simplifyGrid(): Unit = {
    var changed: Boolean = false

    do {
      changed = processCombos()
      moveDownUntilFull()
    } while (changed)
  }

  def swapCandies(x : Int, y : Int, dir : Int): Unit = {
    var x1 : Int = x
    var y1 : Int = y

    var x2 : Int = 0
    var y2 : Int = 0

    dir match {
      case 3 => {
        x2 = x1
        y2 = y1 - 1
      }
      case 2 => {
        x2 = x1 -1
        y2 = y1
      }
      case 1 => {
        x2 = x1
        y2 = y1 + 1
      }
      case 0 => {
        x2 = x1 + 1
        y2 = y1
      }
    }
    var a : Candy = grid(y2)(x2)
    grid(y2)(x2) = grid(y1)(x1)
    grid(y1)(x1) = a
  }





}

object GridManager extends App {
  Candy.init()
  var gridOne : GridManager = new GridManager(12)
  gridOne.displayGrid()
  val simplified: Boolean = gridOne.processCombos()
  println(s"Combos ? $simplified")
  gridOne.displayGrid()
  println("-----------")
  gridOne.moveDownUntilFull()
  gridOne.displayGrid()
}

