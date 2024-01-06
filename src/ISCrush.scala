object ISCrush extends App{

  var running : Boolean = true

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

  def mainLoop(): Unit = {
    while(running){
      gridOne.simplifyGrid()
      gridOne.displayGrid()
      renderer.render(gridOne)
      playerChoice()
    }
  }

  Candy.init()

  var gridOne: GridManager = new GridManager(6)
  var renderer: GridRenderer = new GridRenderer()
  mainLoop()



  /*var gameOne : ISCrush = new ISCrush()

  gameOne.playerChoice()*/
}
