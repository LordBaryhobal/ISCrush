
class ISCrush{
  def playerChoice(): Unit = {
    println("Choose a letter")
    println("Number of the line : ")
    var i = Input.readInt()
    println("Number of the column : ")
    var j = Input.readInt()
  }
}
object ISCrush extends App{

  Candy.init()

  var gridOne: GridManager = new GridManager(12)
  gridOne.displayGrid()

  var gameOne : ISCrush = new ISCrush()


  gameOne.playerChoice()
}
