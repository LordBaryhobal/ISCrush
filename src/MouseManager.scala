import java.awt.event.{MouseEvent, MouseListener}

class MouseManager(val renderer: GridRenderer) extends MouseListener with InputHandler {
  private var startPos: (Int, Int) = (0, 0)
  private var endPos: (Int, Int) = (0, 0)
  private var direction: Int = 0

  override def mousePressed(e: MouseEvent): Unit = {
    val winX: Int = e.getX
    val winY: Int = e.getY

    startPos = ISCrush.renderer.screenToGrid(winX, winY)
  }

  override def mouseReleased(e: MouseEvent): Unit = {
    val winX: Int = e.getX
    val winY: Int = e.getY

    endPos = renderer.screenToGrid(winX, winY)
    val angle : Double = math.atan2(endPos._2 - startPos._2, endPos._1 - startPos._1)
    direction = math.round(angle*2/math.Pi + 4).toInt%4

    ISCrush.setInput(startPos._1, startPos._2, direction)
  }

  override def mouseClicked(e: MouseEvent): Unit = {}

  override def mouseEntered(e: MouseEvent): Unit = {}

  override def mouseExited(e: MouseEvent): Unit = {}
}
