object ISCrush extends App{

  var running : Boolean = true

  def mainLoop(): Unit = {
    while(running){
      gridOne.simplifyGrid()
      gridOne.displayGrid()
      renderer.render()
      val (x: Int, y: Int, dir: Int) = inputHandler.getInput()
      gridOne.swapCandies(x, y, dir)
    }
  }

  Candy.init()

  var gridOne: GridManager = new GridManager(6)
  var renderer: GridRenderer = new GridRenderer(gridOne)

  var inputHandler: InputHandler = _

  println("How would you like to play ?")
  println(" (0) in the console (with keyboard)")
  println(" (1) in a window (with mouse)")
  var inputChoice: Int = Input.readInt()
  inputChoice match {
    case 0 => inputHandler = new ConsoleManager()
    case 1 => {
      val mouseManager: MouseManager = new MouseManager(renderer)
      renderer.window.addMouseListener(mouseManager)
      inputHandler = mouseManager
    }
  }

  mainLoop()
}
