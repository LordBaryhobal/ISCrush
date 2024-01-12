import hevs.graphics.FunGraphics

import java.awt.{Color, Font}
import javax.swing.SwingConstants

class GridRenderer(val gridManager: GridManager, val score : Score) {
  private val FPS: Int = 30
  private val gridSize: Int = 400
  private val titleHeight: Int = 50
  private val scoreWidth: Int = 100

  private val totalWidth: Int = gridSize + scoreWidth
  private val totalHeight: Int = gridSize + titleHeight
  val window: FunGraphics = new FunGraphics(totalWidth, totalHeight)

  private val cellSize: Int = gridSize / gridManager.size
  var animationStartTime: Long = 0
  val ANIMATION_DURATION: Long = 500

  def render(): Unit = {
    window.frontBuffer.synchronized({
      window.clear()

      val curTime: Long = System.currentTimeMillis()

      for (y: Int <- 0 until gridManager.size) {
        for (x: Int <- 0 until gridManager.size) {
          val candy: Candy = gridManager.grid(y)(x)
          var winX: Int = 0
          var winY: Int = 0

          if (candy.hasMoved) {
            val (winX1: Int, winY1: Int) = gridToScreen(candy.oldPos.x, candy.oldPos.y)
            val (winX2: Int, winY2: Int) = gridToScreen(candy.pos.x, candy.pos.y)
            val r: Double = math.max(0, math.min(1, (curTime - animationStartTime) / ANIMATION_DURATION.toDouble))
            winX = math.round((winX2 - winX1) * r + winX1).toInt
            winY = math.round((winY2 - winY1) * r + winY1).toInt

          } else {
            val winPos: (Int, Int) = gridToScreen(x, y)
            winX = winPos._1
            winY = winPos._2
          }

          if (!candy.isEmpty) {
            window.drawTransformedPicture(winX + cellSize / 2, winY + cellSize / 2, 0, 0.2, candy.img)
          }
        }
      }
      if (Score.victory) {
        window.drawFancyString(
          totalWidth/2,
          totalHeight/2,
          "CONGRATULATIONS !!",
          color = Color.WHITE,
          fontSize = 40,
          halign = SwingConstants.CENTER,
          valign = SwingConstants.CENTER,
          outlineColor = Color.BLACK,
          outlineThickness = 2
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
        totalWidth,
        titleHeight - 50 ,
        s"Score : \n ${Score.curPoints}",
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

  def gridToScreen(x: Int, y: Int): (Int, Int) = {
    val winX: Int = cellSize * x
    val winY: Int = cellSize * y + titleHeight
    return (winX, winY)
  }

  def screenToGrid(winX: Int, winY: Int): (Int, Int) = {
    val x: Int = math.floor(winX.toDouble / cellSize).toInt
    val y: Int = math.floor((winY.toDouble - titleHeight) / cellSize).toInt
    return (x, y)
  }
}