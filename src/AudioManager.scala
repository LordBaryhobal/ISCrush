import scala.collection.immutable.HashMap

object AudioManager {
  val audios: Map[String, Audio] = HashMap(
    "swap" -> new Audio("/res/sound/swap.wav"),
    "feedback" -> new Audio("/res/sound/feedback.wav"),
    "hello" -> new Audio("/res/sound/hello.wav"),
    "kru" -> new Audio("/res/sound/kru.wav"),
    "scala" -> new Audio("/res/sound/scala.wav"),
    "starwars" -> new Audio("/res/sound/starwars.wav"),
    "music" -> new Audio("/res/sound/Was-ist-dein-Lieblingsfach.wav", 0.1f),
    "victory" -> new Audio("/res/sound/victory.wav", 0.1f)
  )

  def play(name: String, loop: Boolean = false): Unit = {
    if (audios.contains(name)) {
      audios(name).play(loop)
    }
  }

  def stop(name: String): Unit = {
    if (audios.contains(name)) {
      audios(name).stop()
    }
  }
}
