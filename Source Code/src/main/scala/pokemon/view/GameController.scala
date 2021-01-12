package pokemon.view

import pokemon.MainApp
import pokemon.model.Game
import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Label, ProgressIndicator}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.GridPane
import scalafx.scene.media.{Media, MediaPlayer}
import scalafx.scene.shape.Circle
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@sfxml
class GameController(
    private var pokemonImageLeft: ImageView,
    private val pokemonImageRight: ImageView,
    private val battleground_bg: ImageView,
    private var pokemon1CircleLeft: Circle,
    private var pokemon2CircleLeft: Circle,
    private var pokemon3CircleLeft: Circle,
    private var pokemon1CircleRight: Circle,
    private var pokemon2CircleRight: Circle,
    private var pokemon3CircleRight: Circle,
    private var pokemon1CircleImageLeft: ImageView,
    private var pokemon2CircleImageLeft: ImageView,
    private var pokemon3CircleImageLeft: ImageView,
    private var pokemon1CircleImageRight: ImageView,
    private var pokemon2CircleImageRight: ImageView,
    private var pokemon3CircleImageRight: ImageView,
    private var hpLeft: ProgressIndicator,
    private var hpRight: ProgressIndicator,
    private var hpLeftText: Label,
    private var hpRightText: Label,
    private var pokemonNameLeft: Label,
    private var pokemonNameRight: Label,
    private var gridPaneLeft: GridPane,
    private var gridPaneRight: GridPane,
    private var move1LeftButton: Button,
    private var move2LeftButton: Button,
    private var move3LeftButton: Button,
    private var move4LeftButton: Button,
    private var move1RightButton: Button,
    private var move2RightButton: Button,
    private var move3RightButton: Button,
    private var move4RightButton: Button,
    private var consoleText: Text,
    private var exitButton: Button
  ) {

  val media = new Media(getClass.getResource("../sound/bgm.m4a").toURI.toString)
  val mediaPlayer = new MediaPlayer(media = media)

  val media2 = new Media(getClass.getResource("../sound/victory.m4a").toURI.toString)
  val mediaPlayer2 = new MediaPlayer(media = media2)

  mediaPlayer.setCycleCount(MediaPlayer.Indefinite)
  mediaPlayer.play()

  battleground_bg.image = new Image(getClass.getResourceAsStream("../image/battlegroundBg3.jpg"))
  val deadPNG = "../image/Dead.png"

  //Initialize Game
  var game = new Game("1", "2")
  consoleText.text <== game.gameStatus
  pokemon1CircleLeft.strokeWidth = 4
  pokemon1CircleRight.strokeWidth = 4
  pokemonNameLeft.text = game.playerLeft.deck(0).name
  pokemonNameRight.text = game.playerRight.deck(0).name
  updateHPBar(game.playerLeft.deck(0).hp, game.playerLeft.deck(0).maxHP, hpLeft, hpLeftText)
  updateHPBar(game.playerRight.deck(0).hp, game.playerRight.deck(0).maxHP, hpRight, hpRightText)
  move1LeftButton.text = game.playerLeft.deck(0).move1.getClass.getSimpleName.dropRight(1)
  move2LeftButton.text = game.playerLeft.deck(0).move2.getClass.getSimpleName.dropRight(1)
  move3LeftButton.text = game.playerLeft.deck(0).move3.getClass.getSimpleName.dropRight(1)
  move4LeftButton.text = game.playerLeft.deck(0).move4.getClass.getSimpleName.dropRight(1)
  move1RightButton.text = game.playerRight.deck(0).move1.getClass.getSimpleName.dropRight(1)
  move2RightButton.text = game.playerRight.deck(0).move2.getClass.getSimpleName.dropRight(1)
  move3RightButton.text = game.playerRight.deck(0).move3.getClass.getSimpleName.dropRight(1)
  move4RightButton.text = game.playerRight.deck(0).move4.getClass.getSimpleName.dropRight(1)
  pokemonImageLeft.image = new Image(getClass.getResourceAsStream("../image/" + game.playerLeft.deck(0).name + ".gif"))
  pokemonImageLeft.scaleX = -1
  pokemonImageRight.image = new Image(getClass.getResourceAsStream("../image/" + game.playerRight.deck(0).name + ".gif"))
  pokemon1CircleImageLeft.image = new Image(getClass.getResourceAsStream("../image/" + game.playerLeft.deck(0).name + ".png"))
  pokemon2CircleImageLeft.image = new Image(getClass.getResourceAsStream("../image/" + game.playerLeft.deck(1).name + ".png"))
  pokemon3CircleImageLeft.image = new Image(getClass.getResourceAsStream("../image/" + game.playerLeft.deck(2).name + ".png"))
  pokemon1CircleImageRight.image = new Image(getClass.getResourceAsStream("../image/" + game.playerRight.deck(0).name + ".png"))
  pokemon2CircleImageRight.image = new Image(getClass.getResourceAsStream("../image/" + game.playerRight.deck(1).name + ".png"))
  pokemon3CircleImageRight.image = new Image(getClass.getResourceAsStream("../image/" + game.playerRight.deck(2).name + ".png"))

  game.endGame.onChange ((_, old, newV) => {
    mediaPlayer.stop()
    mediaPlayer2.setCycleCount(MediaPlayer.Indefinite)
    mediaPlayer2.play()
    gridPaneLeft.disable.value = true
    gridPaneRight.disable.value = true
    exitButton.setVisible(true)
  })

  game.turn.onChange ((_, old, newV) => {
    gridPaneLeft.disable.value = gridPaneRight.disable.value
    gridPaneRight.disable.value = !gridPaneRight.disable.value
  })

  game.playerLeft.currentPokemonID.onChange((_, oldID, newID) => {
    pokemonNameLeft.text = game.playerLeft.deck(newID.intValue).name
    move1LeftButton.text = game.playerLeft.deck(newID.intValue).move1.getClass.getSimpleName.dropRight(1)
    move2LeftButton.text = game.playerLeft.deck(newID.intValue).move2.getClass.getSimpleName.dropRight(1)
    move3LeftButton.text = game.playerLeft.deck(newID.intValue).move3.getClass.getSimpleName.dropRight(1)
    move4LeftButton.text = game.playerLeft.deck(newID.intValue).move4.getClass.getSimpleName.dropRight(1)
    updateHPBar(game.playerLeft.deck(newID.intValue).hp, game.playerLeft.deck(newID.intValue).maxHP, hpLeft, hpLeftText)
    pokemonImageLeft.image = new Image(getClass.getResourceAsStream("../image/" + game.playerLeft.deck(newID.intValue).name + ".gif"))
    pokemonImageLeft.scaleX = -1

    newID.intValue() match {
      case 0 =>
        pokemon1CircleLeft.strokeWidth = 4
      case 1 =>
        pokemon2CircleLeft.strokeWidth = 4
      case 2 =>
        pokemon3CircleLeft.strokeWidth = 4
      case default =>
    }
    if (oldID != null)
      oldID.intValue() match {
        case 0 =>
          pokemon1CircleLeft.strokeWidth = 1
        case 1 =>
          pokemon2CircleLeft.strokeWidth = 1
        case 2 =>
          pokemon3CircleLeft.strokeWidth = 1
        case default =>
      }
  })
  game.playerRight.currentPokemonID.onChange((_, oldID, newID) => {
    pokemonNameRight.text = game.playerRight.deck(newID.intValue).name
    move1RightButton.text = game.playerRight.deck(newID.intValue).move1.getClass.getSimpleName.dropRight(1)
    move2RightButton.text = game.playerRight.deck(newID.intValue).move2.getClass.getSimpleName.dropRight(1)
    move3RightButton.text = game.playerRight.deck(newID.intValue).move3.getClass.getSimpleName.dropRight(1)
    move4RightButton.text = game.playerRight.deck(newID.intValue).move4.getClass.getSimpleName.dropRight(1)
    updateHPBar(game.playerRight.deck(newID.intValue).hp, game.playerRight.deck(newID.intValue).maxHP, hpRight, hpRightText)
    pokemonImageRight.image = new Image(getClass.getResourceAsStream("../image/" + game.playerRight.deck(newID.intValue).name + ".gif"))

    newID.intValue() match {
      case 0 =>
        pokemon1CircleRight.strokeWidth = 4
      case 1 =>
        pokemon2CircleRight.strokeWidth = 4
      case 2 =>
        pokemon3CircleRight.strokeWidth = 4
      case default =>
    }
    if (oldID != null)
      oldID.intValue() match {
        case 0 =>
          pokemon1CircleRight.strokeWidth = 1
        case 1 =>
          pokemon2CircleRight.strokeWidth = 1
        case 2 =>
          pokemon3CircleRight.strokeWidth = 1
        case default =>
      }
  })

  game.playerLeft.currentPokemonHP.onChange((_, oldHP, newHP) => {
    updateHPBar(newHP.doubleValue(), game.playerLeft.currentPokemon.maxHP, hpLeft, hpLeftText)
    //if (newHP.doubleValue() <= 0) {
    if (game.playerLeft.currentPokemon.hp <= 0) {
      pokemonImageLeft.image = new Image(getClass.getResourceAsStream(deadPNG))
      pokemonImageLeft.scaleX = 1
      game.playerLeft.currentPokemonID.intValue() match {
        case 0 =>
          pokemon1CircleImageLeft.image = new Image(getClass.getResourceAsStream(deadPNG))
        case 1 =>
          pokemon2CircleImageLeft.image = new Image(getClass.getResourceAsStream(deadPNG))
        case 2 =>
          pokemon3CircleImageLeft.image = new Image(getClass.getResourceAsStream(deadPNG))
        case default =>
      }
    }
  })
  game.playerRight.currentPokemonHP.onChange((_, oldHP, newHP) => {
    updateHPBar(newHP.doubleValue(), game.playerRight.currentPokemon.maxHP, hpRight, hpRightText)
    if (game.playerRight.currentPokemon.hp <= 0) {
      pokemonImageRight.image = new Image(getClass.getResourceAsStream(deadPNG))
      game.playerRight.currentPokemonID.intValue() match {
        case 0 =>
          pokemon1CircleImageRight.image = new Image(getClass.getResourceAsStream(deadPNG))
        case 1 =>
          pokemon2CircleImageRight.image = new Image(getClass.getResourceAsStream(deadPNG))
        case 2 =>
          pokemon3CircleImageRight.image = new Image(getClass.getResourceAsStream(deadPNG))
        case default =>
      }
    }
  })

  def updateHPBar(hp: Double, maxHP: Double, hpBar: ProgressIndicator, hpText: Label): Unit = {
   val hpValue = hp / maxHP
   hpBar.progress = hpValue

   val style =
     if (hpValue >= 0.8) "-fx-accent: YellowGreen;"
     else if (hpValue >= 0.6) "-fx-accent: Gold;"
     else if (hpValue >= 0.4) "-fx-accent: Orange;"
     else "-fx-accent: Tomato;"
   hpBar.setStyle(style)
   hpText.text = hp + "/" + maxHP
  }

  //Player Buttons
  def handleMove1Left (action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerLeft.currentPokemon.attack(game.playerLeft.currentPokemon.move1, game.playerRight)
    }
  }
  def handleMove2Left (action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerLeft.currentPokemon.attack(game.playerLeft.currentPokemon.move2, game.playerRight)
    }
  }
  def handleMove3Left (action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerLeft.currentPokemon.attack(game.playerLeft.currentPokemon.move3, game.playerRight)
    }
  }
  def handleMove4Left (action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerLeft.currentPokemon.enrichment(game.playerLeft.currentPokemon.move4, game.playerRight)
    }
  }
  def handleSwitchLeft (action: ActionEvent): Unit = {
    game.progress {
      game.playerLeft.nextPokemon()
    }
  }
  def handleRunLeft (action: ActionEvent): Unit = {
    game.progress {
      game.playerLeft.lose()
    }
  }
  def handleMove1Right (action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerRight.currentPokemon.attack(game.playerRight.currentPokemon.move1, game.playerLeft)
    }
  }
  def handleMove2Right (action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerRight.currentPokemon.attack(game.playerRight.currentPokemon.move2, game.playerLeft)
    }
  }
  def handleMove3Right (action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerRight.currentPokemon.attack(game.playerRight.currentPokemon.move3, game.playerLeft)
    }
  }
  def handleMove4Right (action: ActionEvent): Unit = {
    game.progress {
      game.gameStatus.value = game.playerRight.currentPokemon.enrichment(game.playerRight.currentPokemon.move4, game.playerLeft)
    }
  }
  def handleSwitchRight (action: ActionEvent): Unit = {
    game.progress {
      game.playerRight.nextPokemon()
    }
  }
  def handleRunRight (action: ActionEvent): Unit = {
    game.progress {
      game.playerRight.lose()
    }
  }
  def handleExit (action: ActionEvent): Unit = {
    mediaPlayer.stop()
    mediaPlayer2.stop()
    MainApp.showTitle()
  }
}
