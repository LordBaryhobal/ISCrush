import Candy.BonusType
import hevs.graphics.utils.GraphicsBitmap


/**
 * Class which controls the logic of the grid (fill candies, process combos)
 * @param size size of the grid (given by the user)
 * @param numberOfTeachers the number of different teachers
 */
class GridManager(val size: Int, val numberOfTeachers: Int) {
  var init: Boolean = false
  var grid: Array[Array[Candy]] = Array.ofDim(size, size)
  private var tmpGrid: Array[Array[Candy]] = Array.ofDim(size, size)
  generateRandomGrid()
  init = true

  /**
   * Fills the grid with random candies
   */
  def generateRandomGrid(): Unit = {
    for (i: Int <- 0 until size) {
      for (j: Int <- 0 until size) {
        val candy: Candy = randomCandy()
        candy.pos.x = j
        candy.pos.y = i
        grid(i)(j) = candy
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
    val randomLetter = util.Random.nextInt(math.min(numberOfTeachers, Candy.models.length))
    val (char : Char, img : GraphicsBitmap, audio : Audio) = Candy.models(randomLetter)
    val candy : Candy = new Candy(char, img, audio)
    return candy
  }

  /**
   * Scans the whole grid and simplifies combos
   *
   * @return true if at least one combo was found, false otherwise
   */
   def processCombos(): Boolean = {
    copyGrid()
    var hasChanged: Boolean = false

    for (y: Int <- 0 until size) {
      var count: Int = 0
      var symbol: Char = 0
      for (x: Int <- 0 until size) {
        val candy: Candy = grid(y)(x)
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
        val candy: Candy = grid(y)(x)
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
   * Simplifies one combo and replaces it with empty candies,
   * or a bonus
   *
   * If a bonus is part of the combo, its power is applied
   *
   * @param x1 X position of top left corner
   * @param y1 Y position of top left corner
   * @param x2 X position of bottom right corner
   * @param y2 Y position of bottom right corner
   */
  private def simplifyCombo(x1: Int, y1: Int, x2: Int, y2: Int): Unit = {
    for (y: Int <- y1 to y2) {
      for (x: Int <- x1 to x2) {
        val candy: Candy = grid(y)(x)
        candy.bonusType match {
          case BonusType.NONE => tmpGrid(y)(x) = Candy.empty()
          case BonusType.COLUMN => {  // Remove whole column
            for (i: Int <- 0 until size) {
              tmpGrid(i)(x) = Candy.empty()
            }
          }
          case BonusType.LINE => {  // Remove whole line
            for (i: Int <- 0 until size) {
              tmpGrid(y)(i) = Candy.empty()
            }
          }
          case BonusType.BOMB => {  // Remove 3x3 square centered on candy
            for (dy: Int <- -1 to 1) {
              if (y + dy >= 0 && x + dy < size) {
                for (dx: Int <- -1 to 1) {
                  if (x + dx >= 0 && x + dx < size) {
                    tmpGrid(y+dy)(x+dx) = Candy.empty()
                  }
                }
              }
            }
          }
        }
      }
    }

    if (init) {
      val sizeCombo : Int = (x2-x1+1)*(y2-y1+1)
      ISCrush.addComboScore(sizeCombo)
      ISCrush.score.combo = true

      if (sizeCombo > 3) {
        var bonusType: Int = BonusType.NONE
        if (sizeCombo == 4) {
          if (x2 != x1) {
            bonusType = BonusType.LINE
          } else {
            bonusType = BonusType.COLUMN
          }
        } else if (sizeCombo == 5) {
          bonusType = BonusType.BOMB
        }
        val x: Int = (x1 + x2)/2
        val y: Int = (y1 + y2)/2
        val model: Candy = grid(y1)(x1)
        val bonus: Candy = new Candy(model.symbol, model.img, model.audio)
        bonus.pos = new Pos(x, y)
        bonus.bonusType = bonusType
        tmpGrid(y)(x) = bonus
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
        if (grid(y)(x).isEmpty) {
          moved = true
          for (y2: Int <- y until 0 by -1) {
            val candy: Candy = grid(y2-1)(x)
            if (init && !candy.hasMoved) {
              candy.oldPos = candy.pos
              candy.hasMoved = true
            }
            candy.pos = new Pos(x, y2)
            grid(y2)(x) = candy
          }
          val newCandy: Candy = randomCandy()
          if (init) {
            newCandy.oldPos = new Pos(x, -1)
            newCandy.hasMoved = true
          }
          newCandy.pos = new Pos(x, 0)
          grid(0)(x) = newCandy
        }
      }
    }
    return moved
  }

  /**
   * Moves down all columns with hole until the grid is full
   */
  def moveDownUntilFull(): Unit = {
    var moved: Boolean = true

    while (moved) {
      moved = moveDown()
    }
  }

  /**
   * Simplifies all combos and moves down columns until there is no combo
   */
  def simplifyGrid(): Boolean = {
    var changed: Boolean = false
    var hasChanged: Boolean = false

    do {
      changed = processCombos()
      if (changed) hasChanged = true
      moveDownUntilFull()
      if (init && changed) {
        ISCrush.playAnimation()
      }
    } while (changed)
    return hasChanged
  }

  /**
   * Swaps a candy with one of its neighbour
   * @param x X position of the first candy
   * @param y Y position of the first candy
   * @param dir direction to the second candy (0=RIGHT/1=DOWN/2=LEFT/3=UP)
   */
  def swapCandies(x : Int, y : Int, dir : Int): Unit = {
    val x1: Int = x
    val y1: Int = y

    var x2: Int = 0
    var y2: Int = 0

    dir match {
      case 3 => {
        x2 = x1
        y2 = y1 - 1
      }
      case 2 => {
        x2 = x1 - 1
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
      case _ => {
        return
      }
    }

    if ((x1 >= size) || (x2 >= size)) {
      return
    }

    if ((x1 < 0) || (x2 < 0)) {
      return
    }

    if ((y1 < 0) || (y2 < 0)) {
      return
    }

    if ((y1 >= size) || (y2 >= size)) {
      return
    }
    val candyA: Candy = grid(y1)(x1)
    val candyB: Candy = grid(y2)(x2)


    if (init) {
      candyA.oldPos = candyA.pos
      candyB.oldPos = candyB.pos
      candyA.hasMoved = true
      candyB.hasMoved = true
    }
    candyA.pos = new Pos(x2, y2)
    candyB.pos = new Pos(x1, y1)

    grid(y1)(x1) = candyB
    grid(y2)(x2) = candyA
  }

  /**
   * Stops the animation when candies don't move anymore
   */
  def clearAnimation(): Unit = {
    for (y: Int <- 0 until size) {
      for (x: Int <- 0 until size) {
        val candy: Candy = grid(y)(x)
        candy.hasMoved = false
      }
    }
  }
}
