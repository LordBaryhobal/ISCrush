import java.awt.event.{MouseEvent, MouseListener}

class MouseManager(val renderer: GridRenderer) extends MouseListener {
  var startPos: (Int, Int) = (0, 0)
  var endPos: (Int, Int) = (0, 0)

  override def mousePressed(e: MouseEvent): Unit = {
    val winX: Int = e.getX
    val winY: Int = e.getY

    startPos = renderer.screenToGrid(winX, winY)
  }

  override def mouseReleased(e: MouseEvent): Unit = {
    val winX: Int = e.getX
    val winY: Int = e.getY

    endPos = renderer.screenToGrid(winX, winY)
    // TODO: call GridManager.swapCandies
  }

  override def mouseClicked(e: MouseEvent): Unit = {}

  override def mouseEntered(e: MouseEvent): Unit = {}

  override def mouseExited(e: MouseEvent): Unit = {}
}
