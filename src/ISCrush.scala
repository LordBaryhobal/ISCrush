object ISCrush extends App{

  def playerChoice(): Unit = {
    println("Choose a letter")
    println("Number of the line : ")
    var y = Input.readInt()
    println("Number of the column : ")
    var x = Input.readInt()
    println("Symbol : ")
    var c = Input.readChar()
    gridOne.swapCandies(x,y,c)
  }

  Candy.init()

  var gridOne: GridManager = new GridManager(12)
  gridOne.displayGrid()
  playerChoice()
  gridOne.displayGrid()



  /*var gameOne : ISCrush = new ISCrush()

  gameOne.playerChoice()*/
}
