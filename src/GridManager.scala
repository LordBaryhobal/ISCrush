class GridManager(size : Int) {
  var grid: Array[Array[Candy]] = Array.ofDim(size, size)

  def displayGrid(): Unit = {
    for (i: Int <- grid.indices) {
      for (j: Int <- grid(i).indices) {
        grid(i)(j) = randomCandy()
        print(grid(i)(j)+ " ")
      }
      println()
    }
  }

  def randomCandy(): Candy = {
    val randomLetter = util.Random.nextInt(Candy.models.length)
    val candy : Candy = new Candy(Candy.models(randomLetter))
    return candy
  }

}

object GridManager extends App {
  var gridOne : GridManager = new GridManager(12)
  gridOne.displayGrid()
}

