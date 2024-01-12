import scala.collection.mutable

object ISCrush extends App{
  var running : Boolean = true
  private var inputReady: Boolean = false
  private var processingInput: Boolean = false
  private var swap: (Int, Int, Int) = (0, 0, 0)

  def setInput(x: Int, y: Int, dir: Int): Unit = {
    swap = (x, y, dir)
    inputReady = true
  }

  def playAnimation(): Unit = {
    renderer.animationStartTime = System.currentTimeMillis()
    Thread.sleep(renderer.ANIMATION_DURATION)
    gridOne.clearAnimation()
  }

  /**
   * Processes an input, checks for and simplifies combos, refilling the grid as needed
   */
  def processInput(): Unit = {
    processingInput = true
    gridOne.swapCandies(swap._1, swap._2, swap._3)
    playAnimation()
    if(!gridOne.simplifyGrid()){
      gridOne.swapCandies(swap._1, swap._2, swap._3)
      playAnimation()
    }
    Score.comboWin()
    gridOne.displayGrid()
    inputReady = false
    processingInput = false
  }

  def addComboScore(sizeCombo : Int ): Unit = {
    Score.curPoints = Score.curPoints + sizeCombo
  }

  def mainLoop(): Unit = {
    Score.combo = false
    Score.curPoints = 0
    gridOne.displayGrid()
    while(running) {
      if (inputReady && !processingInput) {
        new Thread(() => processInput()).start()
      }
      renderer.render()
    }
  }

  Candy.init()



  var inputHandler: InputHandler = _

  println("How would you like to play ?")
  println(" (0) in the console (with keyboard)")
  println(" (1) in a window (with mouse)")
  var inputChoice: Int = Input.readInt()
  var gridOne: GridManager = new GridManager(6)
  var renderer: GridRenderer = new GridRenderer(gridOne, score)
  var score: Score = new Score(400)
  inputChoice match {
    case 0 => inputHandler = new ConsoleManager()
    case 1 => {
      val mouseManager: MouseManager = new MouseManager(renderer, score)
      renderer.window.addMouseListener(mouseManager)
      inputHandler = mouseManager
    }
  }

  mainLoop()
}
