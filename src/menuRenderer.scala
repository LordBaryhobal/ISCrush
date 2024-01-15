import hevs.graphics.FunGraphics

import java.awt.event.{KeyAdapter, KeyEvent}
import java.awt.Color
import javax.swing.SwingConstants

class MenuRenderer {
  private val FPS: Int = 30
  private val gridSize: Int = 600
  private val titleHeight: Int = 100

  private val totalWidth: Int = gridSize
  private val totalHeight: Int = gridSize + titleHeight

  val menuWindow: FunGraphics = new FunGraphics(totalWidth, totalHeight, "ISCrush")

  def renderSizeChoice() {
    menuWindow.drawFancyString(
      totalWidth / 2,
      titleHeight / 2,
      "MENU",
      color = Color.WHITE,
      fontSize = 40,
      halign = SwingConstants.CENTER,
      valign = SwingConstants.CENTER,
      outlineColor = Color.BLACK,
      outlineThickness = 2,
      fontFamily = "Comic Sans MS"
    )

    menuWindow.drawFancyString(
      totalWidth / 2,
      titleHeight / 2 + 70,
      "Size of the grid ? ",
      color = Color.WHITE,
      fontSize = 20,
      halign = SwingConstants.CENTER,
      valign = SwingConstants.CENTER,
      outlineColor = Color.BLACK,
      outlineThickness = 2,
      fontFamily = "Comic Sans MS"
    )

    menuWindow.drawFancyString(
      totalWidth / 2,
      titleHeight / 2 + 100,
      " Choose a number between 2 and 9 ",
      color = Color.WHITE,
      fontSize = 20,
      halign = SwingConstants.CENTER,
      valign = SwingConstants.CENTER,
      outlineColor = Color.BLACK,
      outlineThickness = 2,
      fontFamily = "Comic Sans MS"
    )
  }
  def userChoice(): Int = {

    var x : Int = -1
    menuWindow.setKeyManager(new KeyAdapter() {

      var clicked: Boolean = false
      override def keyPressed(e: KeyEvent): Unit = {
        if (clicked == false) {
          x = (e.getKeyChar).toInt - 48
          println(x)
          clicked = true
        }
      }
    })
    return(x)
  }

  def renderTeacherChoice(): Unit = {
    menuWindow.clear()

    menuWindow.drawFancyString(
      totalWidth / 2,
      titleHeight / 2,
      "MENU",
      color = Color.WHITE,
      fontSize = 40,
      halign = SwingConstants.CENTER,
      valign = SwingConstants.CENTER,
      outlineColor = Color.BLACK,
      outlineThickness = 2,
      fontFamily = "Comic Sans MS"
    )

    menuWindow.drawFancyString(
      totalWidth / 2,
      titleHeight / 2 + 100,
      " How many teachers would you like ?  ",
      color = Color.WHITE,
      fontSize = 20,
      halign = SwingConstants.CENTER,
      valign = SwingConstants.CENTER,
      outlineColor = Color.BLACK,
      outlineThickness = 2,
      fontFamily = "Comic Sans MS"
    )

    menuWindow.drawFancyString(
      totalWidth / 2,
      titleHeight / 2 + 150,
      " Choose a number between 2 to 5 ",
      color = Color.WHITE,
      fontSize = 20,
      halign = SwingConstants.CENTER,
      valign = SwingConstants.CENTER,
      outlineColor = Color.BLACK,
      outlineThickness = 2,
      fontFamily = "Comic Sans MS"
    )

  }


}


object MenuRenderer extends App {
  var menu = new MenuRenderer()
  menu.renderSizeChoice()
  var sizeOfGrid : Int = menu.userChoice()
  menu.renderTeacherChoice()
  var numOfTeacher : Int = menu.userChoice()


}