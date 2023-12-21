import scala.collection.mutable.ArrayBuffer

class Candy(var symbol : Char) {
  override def toString: String = ""+symbol
}

object Candy extends App{
  var models: ArrayBuffer[Char] = new ArrayBuffer()

  def init(): Unit = {
    models = new ArrayBuffer()
    models.addOne('W')
    models.addOne('Z')
  }
}
