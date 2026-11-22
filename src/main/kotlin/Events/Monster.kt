package Events

import Players.Player
import Utilities.getEncounterLostMessage
import Utilities.getEncounterWonMessage

abstract class Monster : Event {
    abstract val combatPower: Int
    abstract val loot: Int
    abstract val damage: Int
    abstract val size: Int

    open fun afterEncounter() {}

    override fun play(player: Player): String {
        val message: String = if (player.combatPower() > combatPower) {
            player.winEncounter(loot)
            getEncounterWonMessage(player, loot)
        } else {
            player.loseEncounter(damage)
            getEncounterLostMessage(player, damage)
        }
        afterEncounter()
        return message
    }
}

class Snail : Monster() {
    override val combatPower: Int = 5
    override val loot: Int = 2
    override val damage: Int = 10
    override val size: Int = 1

    override fun getDescription(): String {
        return "Snail (power $combatPower, loot $loot, damage $damage)"
    }
}

class Slime : Monster() {
    override val combatPower: Int = 12
    override val loot: Int = 5
    override val damage: Int = 25
    override val size: Int = 1

    override fun getDescription(): String {
        return "Slime (power $combatPower, loot $loot, damage $damage)"
    }
}

class Balrog : Monster() {
    override var combatPower: Int = 15
    override val loot: Int = 100
    override val damage: Int = 9001
    override val size: Int = 1

    override fun afterEncounter() {
        combatPower += 2
    }

    override fun getDescription(): String {
        return "Balrog (power $combatPower, loot $loot, damage $damage)"
    }
}

class Pack(private val monsters: List<Monster>) : Monster() {
    override val combatPower: Int
        get() = monsters.sumOf { monster: Monster -> monster.combatPower }

    override val loot: Int
        get() = monsters.sumOf { monster: Monster -> monster.loot }

    override val damage: Int
        get() = monsters.sumOf { monster: Monster -> monster.damage }

    override val size: Int
        get() = monsters.sumOf { monster: Monster -> monster.size }

    override fun afterEncounter() {
        monsters.forEach { monster: Monster -> monster.afterEncounter() }
    }

    override fun getDescription(): String {
        return "Pack of $size members (power $combatPower, loot $loot, damage $damage)"
    }
}