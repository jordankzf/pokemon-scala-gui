package pokemon.view

import pokemon.MainApp
import scalafx.event.ActionEvent
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.media.{Media, MediaPlayer}
import scalafxml.core.macros.sfxml

@sfxml
class TitleController(private val titleBg: ImageView, private val titleLogo: ImageView) {
  val media = new Media(getClass.getResource("../sound/title.m4a").toURI.toString)
  val mediaPlayer = new MediaPlayer(media = media)
  mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
  mediaPlayer.play()
  System.gc()
  titleBg.image = new Image(getClass.getResourceAsStream("../image/titleBg.jpg"))
  titleLogo.image = new Image(getClass.getResourceAsStream("../image/titleLogo.png"))

  def handlePlay (action: ActionEvent): Unit = {
    MainApp.showGame()
    mediaPlayer.stop()
  }
  def handleExit (action: ActionEvent): Unit = {
    System.exit(0)
  }
}