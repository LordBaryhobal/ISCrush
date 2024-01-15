import javax.sound.sampled.{AudioSystem, Clip}
  class Audio(path: String) {
    var audioClip: Clip = _

    val url = classOf[Audio].getResource(path)
    val audioStream = AudioSystem.getAudioInputStream(url)

    audioClip = AudioSystem.getClip.asInstanceOf[Clip]
    audioClip.open(audioStream)


    def play(): Unit = {
      // Open stream and play
      try {
        if (!audioClip.isOpen) audioClip.open()
        audioClip.stop()
        audioClip.setMicrosecondPosition(0)
        audioClip.start()
      } catch {
        case e: Exception =>
          e.printStackTrace()
      }
    }
  }

object listen extends App {
  var aud : Audio = new Audio("/res/sound/music.wav")
  aud.play()
  Input.readInt()
}
