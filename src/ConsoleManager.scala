class ConsoleManager extends InputHandler {
  new Thread(new Runnable {
    override def run(): Unit = {
      while (true) {
        println("Choose a number")
        println("Number of the line : ")
        val y = Input.readInt()
        println("Number of the column : ")
        val x = Input.readInt()
        println("Direction : ")
        val dir = Input.readInt()
        ISCrush.setInput(x, y, dir)
      }
    }
  }).start()
}
