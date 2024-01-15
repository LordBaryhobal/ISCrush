import Candy.BonusType
import hevs.graphics.FunGraphics

import java.awt.{Color, Font}
import javax.swing.SwingConstants

/**
 * This class displays the game
 * @param gridManager allow us to access the grid
 */
class GridRenderer(val gridManager: GridManager) {
  private val FPS: Int = 30
  private val gridSize: Int = 400
  private val titleHeight: Int = 100

  private val totalWidth: Int = gridSize
  private val totalHeight: Int = gridSize + titleHeight
  val window: FunGraphics = new FunGraphics(totalWidth, totalHeight, "ISCrush")

  private val cellSize: Double = gridSize / gridManager.size.toDouble
  var animationStartTime: Long = 0
  val ANIMATION_DURATION: Long = 500


  /**
   * Displays the grid, title and score on the window
   */
  def render(): Unit = {
    window.frontBuffer.synchronized({
      window.clear()

      val curTime: Long = System.currentTimeMillis()

      for (y: Int <- 0 until gridManager.size) {
        for (x: Int <- 0 until gridManager.size) {
          val candy: Candy = gridManager.grid(y)(x)
          var winX: Double = 0
          var winY: Double = 0

          if (candy.hasMoved) {
            val (winX1: Double, winY1: Double) = gridToScreen(candy.oldPos.x, candy.oldPos.y)
            val (winX2: Double, winY2: Double) = gridToScreen(candy.pos.x, candy.pos.y)
            val r: Double = math.max(0, math.min(1, (curTime - animationStartTime) / ANIMATION_DURATION.toDouble))
            winX = (winX2 - winX1) * r + winX1
            winY = (winY2 - winY1) * r + winY1

          } else {
            val winPos: (Double, Double) = gridToScreen(x, y)
            winX = winPos._1
            winY = winPos._2
          }

          if (!candy.isEmpty) {
            val scale: Double = cellSize / math.max(candy.img.getWidth, candy.img.getHeight)
            val cx: Int = math.ceil(winX + cellSize / 2).toInt
            val cy: Int = math.ceil(winY + cellSize / 2).toInt
            window.drawTransformedPicture(cx, cy, 0, scale, candy.img)

            if (candy.bonusType != BonusType.NONE) {
              val txt: String = candy.bonusType match {
                case BonusType.LINE => "Row"
                case BonusType.COLUMN => "Col"
                case BonusType.BOMB => "Bomb"
              }
              window.drawFancyString(cx, cy, txt, color=Color.BLACK, halign=SwingConstants.CENTER, valign=SwingConstants.CENTER, outlineColor=Color.WHITE, outlineThickness=2)
            }
          }
        }
      }
      if (ISCrush.score.victory) {
        window.drawFancyString(
          totalWidth/2,
          totalHeight/2,
          "CONGRATULATIONS !!",
          color = Color.WHITE,
          fontSize = 40,
          halign = SwingConstants.CENTER,
          valign = SwingConstants.CENTER,
          outlineColor = Color.BLACK,
          outlineThickness = 4
        )
      }

      window.drawFancyString(
        totalWidth / 2,
        titleHeight / 2,
        "ISCrush",
        color = Color.WHITE,
        fontSize = 40,
        halign = SwingConstants.CENTER,
        valign = SwingConstants.CENTER,
        outlineColor = Color.BLACK,
        outlineThickness = 3,
        fontFamily = "Comic Sans MS"
      )
      window.drawString(
        totalWidth-10,
        10,
        s"Score : \n ${ISCrush.score.curPoints}",
        color = Color.BLACK,
        fontSize = 20,
        halign = SwingConstants.RIGHT,
        valign = SwingConstants.TOP,
        fontStyle = Font.BOLD,
        fontFamily = "Comic Sans MS"
      )
    })

    window.syncGameLogic(FPS)
  }

  /**
   * Convert the coordinates of the grid to the coordinates of the window
   * @param x X position in the grid
   * @param y Y position in the grid
   * @return a tuple representing the position in window coordinates
   */
  def gridToScreen(x: Int, y: Int): (Double, Double) = {
    val winX: Double = cellSize * x
    val winY: Double = cellSize * y + titleHeight
    return (winX, winY)
  }

  /**
   * Converts the coordinates of the window to the coordinate of the grid
   *
   * @param x X position in the window
   * @param y Y position in the window
   * @return a tuple representing the position in grid coordinates
   */
  def screenToGrid(winX: Double, winY: Double): (Int, Int) = {
    val x: Int = math.floor(winX / cellSize).toInt
    val y: Int = math.floor((winY - titleHeight) / cellSize).toInt
    return (x, y)
  }
}