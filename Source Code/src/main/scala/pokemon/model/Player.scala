package pokemon.model

import pokemon.model.scalafxImpl.ScalafxSound
import scalafx.beans.property.{DoubleProperty, IntegerProperty, ObjectProperty}

import scala.collection.mutable.ArrayBuffer

class Player(val name: String) {
  var pokemonDeathCount = 0
  var deck:ArrayBuffer[Pokemon] = ArrayBuffer.empty[Pokemon]
  generateDeck()
  var currentPokemon: Pokemon = deck(0)
  var currentPokemonID: IntegerProperty = IntegerProperty(0)
  var currentPokemonHP: DoubleProperty = DoubleProperty(currentPokemon.hp)
  var lost = false

  def generateDeck(): Unit = {
    val r = scala.util.Random
    for (i <- 0 until 3) {
      var newPokemon = Array(
        new Bulbasaur() with ScalafxSound,
        new Squirtle() with ScalafxSound,
        new Charmander() with ScalafxSound,
        new Chikorita() with ScalafxSound,
        new Totodile() with ScalafxSound,
        new Cyndaquil() with ScalafxSound
      )
      deck += newPokemon(r.nextInt(newPokemon.length))
    }
  }

  def nextPokemon(): Unit = {
    if (pokemonDeathCount == deck.length) {
      lost = true
    } else {
      do {
        if (currentPokemonID.value == deck.length-1) {
          currentPokemonID.value = 0
        } else {
          currentPokemonID.value += 1
        }
      } while (deck(currentPokemonID.value).hp <= 0)
    currentPokemon = deck(currentPokemonID.value)
    }
  }

  def lose(): Unit = {
    this.lost = true
  }
}