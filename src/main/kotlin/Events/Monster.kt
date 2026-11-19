package Events

abstract class Monster : Event {
    abstract val combatPower: Int
    abstract val loot: Int
    abstract val damage: Int
    abstract val size: Int

    open fun afterEncounter() {}
}

class Snail : Monster() {
    override val combatPower: Int = 5
    override val loot: Int = 2
    override val damage: Int = 10
    override val size: Int = 1

    override fun getDescription(): String {
        return "Snail (Power $combatPower, Loot $loot, Damage $damage)"
    }
}

class Slime : Monster() {
    override val combatPower: Int = 12
    override val loot: Int = 5
    override val damage: Int = 25
    override val size: Int = 1

    override fun getDescription(): String {
        return "Slime (Power $combatPower, Loot $loot, Damage $damage)"
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
        return "Balrog (Power $combatPower, Loot $loot, Damage $damage)"
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
        return "Pack of $size members (Power $combatPower, Loot $loot, Damage $damage)"
    }
}