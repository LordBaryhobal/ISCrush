/**
 * This class manages the score of the game .
 * @param finalPoints number of points that the player needs to win the game
 */
class Score(var finalPoints : Int) {
  var curPoints : Int  = 0
  var victory : Boolean = false
  var combo : Boolean = false

  /**
   * Stops the game when the final score is reached. print all the steps in the console
   */
  def comboWin(): Unit = {
    if(combo){
      println(s"Your current score is : $curPoints")
    }
    if(curPoints >= finalPoints){
      victory = true
      AudioManager.stop("music")
      AudioManager.play("victory")
      println("CONGRATULATIONS !!! ")
      ISCrush.running = false
    }
    combo = false
  }
}