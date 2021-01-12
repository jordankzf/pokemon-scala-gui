package pokemon
import java.net.URL

import javafx.scene.image.Image
import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.animation.PauseTransition
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.util.Duration
import scalafxml.core.{FXMLLoader, NoDependencyResolver}

object MainApp extends JFXApp {
  val rootResource: URL = getClass.getResource("view/RootLayout.fxml")
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load()
  val roots = loader.getRoot[javafx.scene.layout.BorderPane]

  stage = new PrimaryStage {
    title = "Pokemon"
    width = 900
    height = 600
    icons += new Image(getClass.getResource("image/titleLogo.png").toURI.toString)
    scene = new Scene {
      root = roots
    }
  }

  def showLoadingScreen(): Unit = {
    val resource = getClass.getResource("view/LoadingScreen.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
    new PauseTransition(Duration(1000)){
      onFinished = handle {
        showTitle()
      }
    }.play()
  }

  def showTitle(): Unit = {
    val resource = getClass.getResource("view/Title.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  def showGame(): Unit = {
    val resource = getClass.getResource("view/Game.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  showLoadingScreen()
}