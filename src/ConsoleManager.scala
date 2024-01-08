class ConsoleManager extends InputHandler {
  override def getInput(): (Int, Int, Int) = {
    println("Choose a number")
    println("Number of the line : ")
    val y = Input.readInt()
    println("Number of the column : ")
    val x = Input.readInt()
    println("Direction : ")
    val dir = Input.readInt()
    return (x, y, dir)
  }
}
