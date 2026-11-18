package Players

private const val STARTING_LEVEL = 1
private const val STARTING_FORCE = 5
private const val MAX_LEVEL = 10
private const val MIN_FORCE = 0
private const val MIN_HP = 0

class Player(
    private val name: String,
    private val job: Job,
    private val personality: Personality,
) {
    private var level: Int = STARTING_LEVEL
    private var force: Int = STARTING_FORCE
    private val maxHp: Int = job.maxHp
    private var currentHp: Int = maxHp
    private var coins: Int = job.startingCoins

    fun getName(): String = name

    fun getLevel(): Int = level

    fun getForce(): Int = force

    fun getHealthPoints(): Int = currentHp

    fun getCoins(): Int = coins

    fun getDescription(): String =
        "$name, ${job.jobName} with ${personality.personalityName} character (level $level, force $force)"


    fun combatPower(): Int = job.combatPower(force, level)

    fun isKnockedOut(): Boolean = currentHp == MIN_HP

    fun isAtMaxLevel(): Boolean {
        assert(level > 0 && level <= MAX_LEVEL) { "Invalid level: $level" }
        return level >= MAX_LEVEL
    }


    fun winEncounter(loot: Int) {
        levelUp()
        gainCoins(loot)
        takeDamage(job.hpLossOnWin)
    }


    fun loseEncounter(damage: Int) {
        takeDamage(damage)
    }



    fun applySolarEclipse(): Int {
        val before = force
        force = maxOf(MIN_FORCE, force + job.solarEclipseForceDelta)
        return force - before
    }


    fun buyPotions(): Int {
        val amount = personality.potionsToBuy(currentHp, maxHp, coins)
        spendCoins(amount * POTION_COST)
        heal(amount * POTION_HEAL)
        return amount
    }


    private fun levelUp() {
        if (level < MAX_LEVEL) level++
    }

    private fun takeDamage(amount: Int) {
        currentHp = maxOf(MIN_HP, currentHp - amount)
    }

    private fun heal(amount: Int) {
        currentHp = minOf(maxHp, currentHp + amount)
    }

    private fun gainCoins(amount: Int) {
        coins += amount
    }

    private fun spendCoins(amount: Int) {
        coins -= amount
    }

}
