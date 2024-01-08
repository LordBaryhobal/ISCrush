import scala.collection.mutable

object ISCrush extends App{
  var running : Boolean = true
  private var inputReady: Boolean = false
  private var swap: (Int, Int, Int) = (0, 0, 0)

  def setInput(x: Int, y: Int, dir: Int): Unit = {
    swap = (x, y, dir)
    inputReady = true
  }
  def processInput(): Unit = {
    gridOne.swapCandies(swap._1, swap._2, swap._3)
    gridOne.simplifyGrid()
    gridOne.displayGrid()
    inputReady = false
  }

  def mainLoop(): Unit = {
    gridOne.displayGrid()
    while(running) {
      if (inputReady) {
        processInput()
      }

      renderer.render()
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
