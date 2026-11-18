package Players

private const val WARRIOR_MAX_HP = 150
private const val DEFAULT_MAX_HP = 100
private const val DEFAULT_STARTING_COINS = 10
private const val ARCHER_STARTING_COINS = 20
private const val MELEE_HP_LOSS_ON_WIN = 10
private const val NO_HP_LOSS_ON_WIN = 0
private const val WARRIOR_FORCE_MULTIPLIER = 2
private const val SOLAR_ECLIPSE_FORCE_PENALTY = -1
private const val SOLAR_ECLIPSE_FORCE_BONUS = 1

interface Job {
    val jobName: String
    val maxHp: Int
    val startingCoins: Int
    val hpLossOnWin: Int
    val solarEclipseForceDelta: Int
    fun combatPower(force: Int, level: Int): Int
}

object Warrior : Job {
    override val jobName = "Warrior"
    override val maxHp = WARRIOR_MAX_HP
    override val startingCoins = DEFAULT_STARTING_COINS
    override val hpLossOnWin = MELEE_HP_LOSS_ON_WIN
    override val solarEclipseForceDelta = SOLAR_ECLIPSE_FORCE_PENALTY
    override fun combatPower(force: Int, level: Int) = force * WARRIOR_FORCE_MULTIPLIER + level
}

object Archer : Job {
    override val jobName = "Archer"
    override val maxHp = DEFAULT_MAX_HP
    override val startingCoins = ARCHER_STARTING_COINS
    override val hpLossOnWin = NO_HP_LOSS_ON_WIN
    override val solarEclipseForceDelta = SOLAR_ECLIPSE_FORCE_PENALTY
    override fun combatPower(force: Int, level: Int) = force + level
}

object Magician : Job {
    override val jobName = "Magician"
    override val maxHp = DEFAULT_MAX_HP
    override val startingCoins = DEFAULT_STARTING_COINS
    override val hpLossOnWin = NO_HP_LOSS_ON_WIN
    override val solarEclipseForceDelta = SOLAR_ECLIPSE_FORCE_BONUS
    override fun combatPower(force: Int, level: Int) = force + level
}
