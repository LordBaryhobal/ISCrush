import scala.collection.mutable.ArrayBuffer

/**
 * A simple class to represent candies
 * @param symbol The symbol of the candy type
 */
class Candy(var symbol : Char) {
  override def toString: String = ""+symbol
}

object Candy extends App{
  var models: ArrayBuffer[Char] = new ArrayBuffer()

  def init(): Unit = {
    models = new ArrayBuffer()
    models.addOne('A')
    models.addOne('B')
    models.addOne('C')
    models.addOne('D')
    models.addOne('E')
    models.addOne('F')
  }

  def empty(): Candy ={
    return new Candy(' ')
  }
}
