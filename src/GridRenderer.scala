import hevs.graphics.FunGraphics

import java.awt.font.FontRenderContext
import java.awt.{Color, Font}

class GridRenderer(val gridManager: GridManager) {
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

      window.drawString(totalWidth/2-75, titleHeight/2+20, "ISCrush", Color.BLUE, 40)
      window.setColor(Color.BLUE)

      for (y: Int <- 0 until gridManager.size) {
        for (x: Int <- 0 until gridManager.size) {
          val (winX: Int, winY: Int) = gridToScreen(x, y)

          //val centerX: Int = winX + cellSize / 2
          //val centerY: Int = winY + cellSize / 2

          window.drawCircle(winX, winY, cellSize)
          val candy: Candy = gridManager.grid(y)(x)
          window.drawString(winX + cellSize/2, winY + cellSize/2, ""+candy.symbol)
        }
      }
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