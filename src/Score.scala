/**
 * This class manages a score.
 *
 * @param finalPoints number of points that the player needs to win the game
 */
class Score(var finalPoints : Int) {
  var curPoints : Int  = 0
  var victory : Boolean = false
  var combo : Boolean = false

  /**
   * Prints in the console when there is a combo and displays the current score. It stops the
   * game when the final score is reached
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