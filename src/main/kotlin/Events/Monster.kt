package Events

abstract class Monster : Event {
    abstract val combatPower: Int
    abstract val loot: Int
    abstract val damage: Int
    abstract val size: Int

    open fun afterEncounter() {}
}
