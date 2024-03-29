import java.awt.event.{MouseEvent, MouseListener}

/**
 * This class allow us to use the mouse to play the game
 * @param renderer needed to convert coordinates
 */
class MouseManager(val renderer: GridRenderer) extends MouseListener with InputHandler {
  private var startPos: (Int, Int) = (0, 0)
  private var endPos: (Int, Int) = (0, 0)
  private var direction: Int = 0

  /**
   * obtains the position of the cursor inside the window when the mouse is pressed
   * @param e needs the class MouseEvent
   */
  override def mousePressed(e: MouseEvent): Unit = {
    val winX: Int = e.getX
    val winY: Int = e.getY

    startPos = renderer.screenToGrid(winX, winY)
  }

  /**
   * This method changes the position of the candy when we release the click of the mouse
   * @param e needs the class MouseEvent
   */
  override def mouseReleased(e: MouseEvent): Unit = {
    val winX: Int = e.getX
    val winY: Int = e.getY

    endPos = renderer.screenToGrid(winX, winY)
    val angle : Double = math.atan2(endPos._2 - startPos._2, endPos._1 - startPos._1)
    direction = math.round(angle * 2 / math.Pi + 4).toInt % 4

    ISCrush.setInput(startPos._1, startPos._2, direction)
  }

  override def mouseClicked(e: MouseEvent): Unit = {}

  override def mouseEntered(e: MouseEvent): Unit = {}

  override def mouseExited(e: MouseEvent): Unit = {}
}
