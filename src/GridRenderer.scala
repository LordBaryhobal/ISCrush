import hevs.graphics.FunGraphics
import hevs.graphics.utils.GraphicsBitmap
import java.awt.font.FontRenderContext
import java.awt.{Color, Font}

class GridRenderer(val gridManager: GridManager, val score : Score) {
  val gridSize: Int = 400
  val titleHeight: Int = 50
  val scoreWidth: Int = 100

  val totalWidth: Int = gridSize + scoreWidth
  val totalHeight: Int = gridSize + titleHeight
  val window: FunGraphics = new FunGraphics(totalWidth, totalHeight)

  val cellSize: Int = gridSize / gridManager.size



  def render(): Unit = {
    window.frontBuffer.synchronized({
      window.clear()


      window.setColor(Color.BLUE)


      for (y: Int <- 0 until gridManager.size) {
        for (x: Int <- 0 until gridManager.size) {
          val (winX: Int, winY: Int) = gridToScreen(x, y)

          //val centerX: Int = winX + cellSize / 2
          //val centerY: Int = winY + cellSize / 2
          val candy: Candy = gridManager.grid(y)(x)
          if (!candy.isEmpty()) {
            window.drawTransformedPicture(winX + cellSize / 2, winY + cellSize / 2, 0, 0.2, candy.img)
          }
        }
      }
      if (Score.victory == true) {
        window.drawString(totalWidth / 2 - 250, titleHeight / 4 + 200, "CONGRATULATIONS !! ", Color.BLUE, 40)
      }

      window.drawString(totalWidth / 2 - 75, titleHeight / 2 + 20, "ISCrush", Color.BLUE, 40)
      window.drawString(totalWidth / 2 + 140, titleHeight / 2 + 20, s"Score : \n ${Score.curPoints}", Color.BLUE, 20)

    })

    window.syncGameLogic(30)
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

object GridRenderer {
}