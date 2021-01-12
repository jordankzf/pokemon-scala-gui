package pokemon.model
import PokemonMove._
import scalafx.beans.property.BooleanProperty

abstract class Pokemon extends MPlayer{
  val name: String
  val maxHP: Double
  var hp: Double
  var attack: Int
  var defense: Int
  val pokemonType: String
  var move1: PhysicalMove
  var move2: PhysicalMove
  var move3: PhysicalMove
  var move4: StatusMove

  def attack(chosenMove: PhysicalMove, targetPlayer: Player): String = {
    //Damage = Move attack x Pokemon attack / 5 / Opponent Pokemon Defense x Modifier
    val modifier: Double = typeLUT.matrix(typeLUT.map.apply(this.pokemonType))(typeLUT.map.apply(targetPlayer.currentPokemon.pokemonType))
    var damage: Double = chosenMove.dealDamage * this.attack / 5 / targetPlayer.currentPokemon.defense * modifier
    if (damage < 1) damage = 1
    targetPlayer.currentPokemon.hp -= damage

    if (targetPlayer.currentPokemon.hp <= 0) {
      targetPlayer.currentPokemon.hp = 0
      targetPlayer.currentPokemonHP.value = targetPlayer.currentPokemon.hp
      targetPlayer.pokemonDeathCount += 1
      playDeathSound()
      targetPlayer.nextPokemon()
    } else {
      playAttackSound()
      targetPlayer.currentPokemonHP.value = targetPlayer.currentPokemon.hp
    }

    if (modifier > 1) {
      "Damage: " + damage + ", it is super effective!"
    } else if (modifier < 1) {
      "Damage: " + damage + ", it is not very effective."
    } else {
      "Damage: " + damage
    }
  }

  def enrichment(chosenMove: StatusMove, targetPlayer: Player): String = {
    var targetPokemon: Pokemon = null
    if (chosenMove.targetSelf) {
      targetPokemon = this
    } else {
      targetPokemon = targetPlayer.currentPokemon
    }
    chosenMove.statusName match {
      case "Defense" =>
        targetPokemon.defense += chosenMove.statusAdjustment
		if (targetPokemon.defense < 1) targetPokemon.defense = 1
        (if(chosenMove.targetSelf) "Your Pokemon's " else "Opponent's Pokemon ") +"Defense stat " +  chosenMove.statusAdjustment.toString
      case "Attack" =>
        targetPokemon.attack += chosenMove.statusAdjustment
		if (targetPokemon.attack < 1) targetPokemon.attack = 1
        (if(chosenMove.targetSelf) "Your Pokemon's " else "Opponent's Pokemon ") + "Attack stat " +  chosenMove.statusAdjustment.toString
      case default =>
        "No effect"
    }
  }
}

object typeLUT {
  val map: Map[String, Int] = Map("Fire" -> 0, "Water" -> 1, "Grass" -> 2)
  val matrix: Array[Array[Double]] = Array.ofDim[Double](3,3)
  matrix(0) = Array(0.5,0.5,2.0)
  matrix(1) = Array(2.0,0.5,0.5)
  matrix(2) = Array(0.5,2.0,0.5)
}

abstract class Move {
}

abstract class PhysicalMove extends Move{
  val dealDamage: Int
}

abstract class StatusMove extends Move{
  val statusName: String
  val statusAdjustment: Int
  val targetSelf: Boolean
}

trait Fire {
  val pokemonType = "Fire"
}

trait Grass {
  val pokemonType = "Grass"
}

trait Water {
  val pokemonType = "Water"
}
object PokemonMove {

  object Tackle extends PhysicalMove {
    val dealDamage = 49
  }

  object Headbutt extends PhysicalMove {
    val dealDamage = 70
  }

  object Barrage extends PhysicalMove {
    val dealDamage = 15
  }

  object Bite extends PhysicalMove {
    val dealDamage = 60
  }

}

object Leer extends StatusMove {
  val statusName = "Defense"
  val statusAdjustment:Int = 30
  val targetSelf = true
}

object Growl extends StatusMove {
  val statusName = "Attack"
  val statusAdjustment:Int = -40
  val targetSelf = false
}

object TailWhip extends StatusMove {
  val statusName = "Defense"
  val statusAdjustment:Int = -30
  val targetSelf = false
}

abstract class Bulbasaur extends Pokemon with Grass {
  val name = "Bulbasaur"
  val maxHP = 45
  var hp:Double = maxHP
  var attack = 49
  var defense = 49
  var move1: PhysicalMove = Tackle
  var move2: PhysicalMove = Headbutt
  var move3: PhysicalMove= Barrage
  var move4: StatusMove = Growl
}

abstract class Squirtle extends Pokemon with Water {
  val name = "Squirtle"
  val maxHP = 44
  var hp:Double = maxHP
  var attack = 48
  var defense = 65
  var move1: PhysicalMove = Bite
  var move2: PhysicalMove = Tackle
  var move3: PhysicalMove= Barrage
  var move4: StatusMove = TailWhip
}

abstract class Charmander extends Pokemon with Fire {
  val name = "Charmander"
  val maxHP = 39
  var hp:Double = maxHP
  var attack = 52
  var defense = 43
  var move1: PhysicalMove = Bite
  var move2: PhysicalMove = Tackle
  var move3: PhysicalMove= Headbutt
  var move4: StatusMove = Leer
}

abstract class Chikorita extends Pokemon with Grass {
  val name = "Chikorita"
  val maxHP = 45
  var hp:Double = maxHP
  var attack = 49
  var defense = 65
  var move1: PhysicalMove = Tackle
  var move2: PhysicalMove = Headbutt
  var move3: PhysicalMove= Barrage
  var move4: StatusMove = Growl
}

abstract class Totodile extends Pokemon with Water {
  val name = "Totodile"
  val maxHP = 50
  var hp:Double = maxHP
  var attack = 65
  var defense = 64
  var move1: PhysicalMove = Bite
  var move2: PhysicalMove = Tackle
  var move3: PhysicalMove= Barrage
  var move4: StatusMove = TailWhip
}

abstract class Cyndaquil extends Pokemon with Fire {
  val name = "Cyndaquil"
  val maxHP = 39
  var hp:Double = maxHP
  var attack = 52
  var defense = 43
  var move1: PhysicalMove = Bite
  var move2: PhysicalMove = Tackle
  var move3: PhysicalMove= Headbutt
  var move4: StatusMove = Leer
}
