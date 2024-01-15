object ISCrush {
  var running : Boolean = true
  private var inputReady: Boolean = false
  private var processingInput: Boolean = false
  private var swap: (Int, Int, Int) = (0, 0, 0)
  private var gridOne: GridManager = _
  private var renderer: GridRenderer = _
  var score: Score = _

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
  private def processInput(): Unit = {
    processingInput = true
    gridOne.swapCandies(swap._1, swap._2, swap._3)
    playAnimation()
    if(!gridOne.simplifyGrid()){
      gridOne.swapCandies(swap._1, swap._2, swap._3)
      playAnimation()
    }
    score.comboWin()
    gridOne.displayGrid()
    inputReady = false
    processingInput = false
  }

  def addComboScore(sizeCombo : Int ): Unit = {
    score.curPoints = score.curPoints + sizeCombo
  }

  private def mainLoop(): Unit = {
    score.combo = false
    score.curPoints = 0
    gridOne.displayGrid()
    while(running) {
      if (inputReady && !processingInput) {
        new Thread(() => processInput()).start()
      }
      renderer.render()
    }
    renderer.render()
  }

  def main(args: Array[String]): Unit = {
    Candy.init()

    var inputHandler: InputHandler = null

    println("How would you like to play ?")
    println(" (0) in the console (with keyboard)")
    println(" (1) in a window (with mouse)")
    val inputChoice: Int = Input.readInt()

    gridOne = new GridManager(Candy.SizeAndNum._1)
    renderer = new GridRenderer(gridOne)
    score = new Score(100)
    inputChoice match {
      case 0 => inputHandler = new ConsoleManager()
      case 1 => {
        val mouseManager: MouseManager = new MouseManager(renderer)
        renderer.window.addMouseListener(mouseManager)
        inputHandler = mouseManager
      }
      case _ => {
        println("Invalid value")
        return
      }
    }

    mainLoop()
  }
}
