import hevs.graphics.utils.GraphicsBitmap

/**
 * A simple class to represent candies
 * @param symbol The symbol of the candy type
 */
class Candy(var symbol : Char, var img : GraphicsBitmap) {
  var oldPos: Pos = new Pos(0, 0)
  var pos: Pos = new Pos(0, 0)
  var hasMoved: Boolean = false

  override def toString: String = ""+symbol

  def isEmpty: Boolean = symbol == ' '
}

object Candy {
  var models: Array[(Char,GraphicsBitmap)] = Array(
    ('A', new GraphicsBitmap("/res/candies/fauchere.jpg")),
    ('B', new GraphicsBitmap("/res/candies/jacquemet.jpg")),
    ('C', new GraphicsBitmap("/res/candies/lafargue.jpg")),
    ('D', new GraphicsBitmap("/res/candies/mudry.jpg")),
    ('E', new GraphicsBitmap("/res/candies/zahno.jpg"))
  )

  def empty(): Candy = {
    return new Candy(' ', new GraphicsBitmap("/res/candies/fauchere.jpg") )
  }
}
