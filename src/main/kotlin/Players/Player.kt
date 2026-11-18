package Players

private const val STARTING_LEVEL = 1
private const val STARTING_FORCE = 5

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
}
