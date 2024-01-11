import hevs.graphics.utils.GraphicsBitmap

import scala.collection.mutable.ArrayBuffer

/**
 * A simple class to represent candies
 * @param symbol The symbol of the candy type
 */
class Candy(var symbol : Char, var img : GraphicsBitmap) {
  var oldPos: Pos = new Pos(0, 0)
  var pos: Pos = new Pos(0, 0)
  var hasMoved: Boolean = false

  override def toString: String = ""+symbol

  def isEmpty(): Boolean = symbol == ' '
}

object Candy extends App{
  var models: ArrayBuffer[(Char,GraphicsBitmap)] = new ArrayBuffer()

  def init(): Unit = {
    models = new ArrayBuffer()
    val Fau = new GraphicsBitmap("/res/candies/fauchere.jpg")
    val Jacq = new GraphicsBitmap("/res/candies/jacquemet.jpg")
    val Lafa = new GraphicsBitmap("/res/candies/lafargue.jpg")
    val Mudr = new GraphicsBitmap("/res/candies/mudry.jpg")
    val Zahn = new GraphicsBitmap("/res/candies/zahno.jpg")

    models.addOne(('A',Fau))
    models.addOne(('B',Jacq))
    models.addOne(('C',Lafa))
    models.addOne(('D',Mudr))
    models.addOne(('E',Zahn))
  }

  def empty(): Candy = {
    return new Candy(' ', new GraphicsBitmap("/res/candies/fauchere.jpg") )
  }
}
