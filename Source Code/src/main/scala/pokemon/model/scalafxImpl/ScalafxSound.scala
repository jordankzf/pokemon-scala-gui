package pokemon.model.scalafxImpl

import pokemon.model.MPlayer
import scalafx.scene.media.{Media, MediaPlayer}

trait ScalafxSound extends MPlayer {

  def playSelectedSound(filename: String, loop: Boolean = false): Unit ={
    val media = new Media(getClass.getResource("../sound/" + filename).toURI().toString())
    val player = new MediaPlayer(media = media)
    if (loop) player.setCycleCount(MediaPlayer.Indefinite)
    player.stop()
    player.play()
  }
  override def playAttackSound(): Unit = {
    playSelectedSound("oof.wav")
  }
  override def playDeathSound(): Unit = {
    playSelectedSound("death_scream.wav")
  }
}
