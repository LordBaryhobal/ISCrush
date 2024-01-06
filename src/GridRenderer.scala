import hevs.graphics.FunGraphics

import java.awt.font.FontRenderContext
import java.awt.{Color, Font}

class GridRenderer {
  val gridSize: Int = 400
  val titleHeight: Int = 50
  val scoreWidth: Int = 100

  val totalWidth: Int = gridSize + scoreWidth
  val totalHeight: Int = gridSize + titleHeight
  val window: FunGraphics = new FunGraphics(totalWidth, totalHeight)

  def render(gridManager: GridManager): Unit = {
    window.clear()

    window.drawString(totalWidth/2-75, titleHeight/2+20, "ISCrush", Color.BLUE, 40)

    val cellSize: Int = gridSize / gridManager.size
    window.setColor(Color.BLUE)

    for (y: Int <- 0 until gridManager.size) {
      for (x: Int <- 0 until gridManager.size) {
        val winX: Int = cellSize * x
        val winY: Int = cellSize * y + titleHeight

        //val centerX: Int = winX + cellSize / 2
        //val centerY: Int = winY + cellSize / 2

        window.drawCircle(winX, winY, cellSize)
        val candy: Candy = gridManager.grid(y)(x)
        window.drawString(winX + cellSize/2, winY + cellSize/2, ""+candy.symbol)
      }
    }
  }
}

object GridRenderer {
}