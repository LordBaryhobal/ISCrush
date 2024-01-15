import Candy.BonusType
import hevs.graphics.utils.GraphicsBitmap

/**
 * A simple class to represent candies
 * @param symbol The symbol of the candy type
 * @param img The image of the candy type
 * @param audio sound of candy when moving
 */
class Candy(var symbol : Char, var img : GraphicsBitmap, var audio : Audio) {
  var oldPos: Pos = new Pos(0, 0)
  var pos: Pos = new Pos(0, 0)
  var hasMoved: Boolean = false
  var bonusType: Int = BonusType.NONE

  override def toString: String = ""+symbol

  def isEmpty: Boolean = symbol == ' '
}

object Candy {
  var models: Array[(Char,GraphicsBitmap,Audio)] = Array(
    ('A', new GraphicsBitmap("/res/candies/fauchere.jpg"), new Audio("/res/sound/hello.wav")),
    ('B', new GraphicsBitmap("/res/candies/jacquemet.jpg"), new Audio("/res/sound/starwars.wav")),
    ('C', new GraphicsBitmap("/res/candies/lafargue.jpg"), new Audio("/res/sound/feedback.wav")),
    ('D', new GraphicsBitmap("/res/candies/mudry.jpg"), new Audio("/res/sound/scala.wav")),
    ('E', new GraphicsBitmap("/res/candies/zahno.jpg"), new Audio("/res/sound/kru.wav")),
  )

  /**
   * Create an empty candy
   */
  def empty(): Candy = {
    return new Candy(' ', new GraphicsBitmap("/res/candies/fauchere.jpg"),  new Audio("/res/sound/kru.wav"))
  }

  object BonusType {
    val NONE: Int = 0
    val LINE: Int = 1
    val COLUMN: Int = 2
    val BOMB: Int = 3
  }
}
