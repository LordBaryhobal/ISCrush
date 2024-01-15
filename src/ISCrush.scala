object ISCrush {
  var running : Boolean = true
  private var inputReady: Boolean = false
  private var processingInput: Boolean = false
  private var swap: (Int, Int, Int) = (0, 0, 0)
  private var gridManager: GridManager = _
  private var renderer: GridRenderer = _
  var score: Score = _

  def setInput(x: Int, y: Int, dir: Int): Unit = {
    swap = (x, y, dir)
    inputReady = true
  }

  def playAnimation(): Unit = {
    renderer.animationStartTime = System.currentTimeMillis()
    Thread.sleep(renderer.ANIMATION_DURATION)
    gridManager.clearAnimation()
  }

  /**
   * Processes an input, checks for and simplifies combos, refilling the grid as needed
   */
  private def processInput(): Unit = {
    processingInput = true
    gridManager.swapCandies(swap._1, swap._2, swap._3)
    playAnimation()
    if(!gridManager.simplifyGrid()){
      gridManager.swapCandies(swap._1, swap._2, swap._3)
      playAnimation()
    }
    score.comboWin()
    gridManager.displayGrid()
    inputReady = false
    processingInput = false
  }

  def addComboScore(sizeCombo : Int ): Unit = {
    score.curPoints = score.curPoints + sizeCombo
  }

  private def mainLoop(): Unit = {
    score.combo = false
    score.curPoints = 0
    gridManager.displayGrid()
    while(running) {
      if (inputReady && !processingInput) {
        new Thread(() => processInput()).start()
      }
      renderer.render()
    }
    renderer.render()
  }

  def chooseDifficulty(): (Int, Int) = {
    println("Choose the size of your grid (> 2) : ")
    val gridSize = math.max(3, Input.readInt())
    println(s"Choose the number of teacher (> 1 && <= ${Candy.models.length}) : ")
    val numberOfTeachers = math.min(math.max(2, Input.readInt()), Candy.models.length)
    return (gridSize, numberOfTeachers)
  }

  def main(args: Array[String]): Unit = {
    val (gridSize: Int, numberOfTeachers: Int) = chooseDifficulty()

    var inputHandler: InputHandler = null

    println("How would you like to play ?")
    println(" (0) in the console (with keyboard)")
    println(" (1) in a window (with mouse)")
    val inputChoice: Int = Input.readInt()

    gridManager = new GridManager(gridSize, numberOfTeachers)
    renderer = new GridRenderer(gridManager)
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
