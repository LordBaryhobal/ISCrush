class Score(var finalPoints : Int) {
  var curPoints : Int  = 0
  var victory : Boolean = false
  var combo : Boolean = false

  /**
   * This method print in the console when there is a combo and display the current score. It stops the
   * game when the final score is reached.
   */
  def comboWin(): Unit = {
    if(combo){
      println(s"Your current score is : $curPoints")
    }
    if(curPoints >= finalPoints){
      victory = true
      println("CONGRATULATIONS !!! ")
      ISCrush.running = false
    }
    combo = false
  }
}