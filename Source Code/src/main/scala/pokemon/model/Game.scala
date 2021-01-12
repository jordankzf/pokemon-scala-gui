package pokemon.model

import scalafx.beans.property.{BooleanProperty, StringProperty}

class Game(
    val player1Name: String,
    val player2Name: String
  ) {
  val playerLeft = new Player(player1Name)
  val playerRight = new Player(player2Name)

  var endGame: BooleanProperty = BooleanProperty(false)

  //false - player1, true - player2
  var turn: BooleanProperty = BooleanProperty(false)

  var gameStatus: StringProperty = new StringProperty()

  def next(): Unit = {
    turn.value = !turn.value
  }
  def progress(activity : => Unit): Unit = {
    activity
    if (isEndGame) {
      endGame.value = true
      gameStatus.value = "Player " + winner.name + " Won!"
    } else {
      next()
    }
  }
  def isEndGame: Boolean = {
    playerLeft.lost || playerRight.lost
  }
  def winner: Player = {
    if (playerLeft.lost) playerRight
    else if (playerRight.lost) playerLeft
    else null
  }
  def loser: Player = {
    if (playerLeft.lost) playerLeft
    else if (playerRight.lost) playerRight
    else null
  }
}
