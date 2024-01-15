import java.net.URL
import javax.sound.sampled.{AudioInputStream, AudioSystem, Clip, Control, FloatControl}

/**
 * Simple class to manage audio clips
 * @param path path of the sound resource
 */
class Audio(val path: String, val volume: Float = 1.0f) {
  var audioClip: Clip = _

  val url: URL = classOf[Audio].getResource(path)
  val audioStream: AudioInputStream = AudioSystem.getAudioInputStream(url)

  audioClip = AudioSystem.getClip
  audioClip.open(audioStream)
  val volumeControl: FloatControl = audioClip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl]
  setVolume(volume)

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

  def setVolume(volume: Float): Unit = {
    volumeControl.setValue(20f * math.log10(volume).toFloat)
  }
}
