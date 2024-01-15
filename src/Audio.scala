import java.net.URL
import javax.sound.sampled.{AudioInputStream, AudioSystem, Clip}

/**
 * Simple class to manage audio clips
 * @param path path of the sound resource
 */
class Audio(val path: String) {
  var audioClip: Clip = _

  val url: URL = classOf[Audio].getResource(path)
  val audioStream: AudioInputStream = AudioSystem.getAudioInputStream(url)

  audioClip = AudioSystem.getClip
  audioClip.open(audioStream)

  /**
   * Starts playing the sound
   *
   * If the sound was already playing, it is played again from the beginning
   * @param loop whether the sound should loop when reaching the end or not
   */
  def play(loop: Boolean = false): Unit = {
    // Open stream and play
    try {
      if (!audioClip.isOpen) audioClip.open()
      audioClip.stop()
      audioClip.setMicrosecondPosition(0)
      if (loop) {
        audioClip.loop(Clip.LOOP_CONTINUOUSLY)
      } else {
        audioClip.start()
      }
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  /**
   * Stops playing the sound
   */
  def stop(): Unit = {
    audioClip.stop()
  }
}

object Audio extends App {
  var audio: Audio = new Audio("/res/sound/music.wav")
  audio.play()
  Input.readInt()
}
