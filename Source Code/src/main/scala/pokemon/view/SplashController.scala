package pokemon.view

import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml

@sfxml
class SplashController(private val bg: ImageView) {
  bg.image = new Image(getClass().getResourceAsStream("../image/loadingScreen.jpg"))
}
