package Players
import Events.Event
import java.io.Reader

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

fun parsePlayers(playersReader: Reader): MutableList<Player> {
    val playerInputList: List<String> =
        playersReader.readLines().asSequence().flatMap { line: String -> line.split(" ", "\t").asSequence() }
            .filter { word: String -> word.isNotEmpty() }.toList()
    if (playerInputList.size !in 6..18 || playerInputList.size % 3 != 0) throw IllegalArgumentException("Invalid Players File")
    val playerChunkList : List<List<String>> = playerInputList.chunked(3)
    val iterator: Iterator<List<String>> = playerChunkList.iterator()
    val playersList: MutableList<Player> = mutableListOf()
    while (iterator.hasNext()) {
        val nextPlayer : Player = readNextPlayer(iterator)
        playersList.add(nextPlayer)
    }

    if (playersList.size !in 2..6) throw IllegalArgumentException("Invalid Players File")
    if (playersList.map { player: Player -> player.getName() }.toSet().size != playersList.size) {
        throw IllegalArgumentException("Invalid Players File")
    }
    return playersList
}

private fun readNextPlayer(iterator: Iterator<List<String>>) : Player {
    if (!iterator.hasNext()) {
        throw IllegalArgumentException("Invalid Players File")
    }
    val playerInfo : List<String> = iterator.next()
    assert(playerInfo.size == 3) { "Invalid Players File" }
    val (name: String, jobStr:String, characterTypeStr: String) = playerInfo
    if (jobStr !in listOf("Warrior", "Archer", "Magician")) {
        throw IllegalArgumentException("Invalid Players File")
    }
    if (name.length !in 3..15 || !name.all { ch: Char -> ch in 'a'..'z' || ch in 'A'..'Z' }) {
        throw IllegalArgumentException("Invalid Players File")
    }
    if (characterTypeStr !in listOf("Responsible", "RiskTaking")) {
        throw IllegalArgumentException("Invalid Players File")
    }
    val job: Job = when(jobStr){
        "Warrior" -> Warrior
        "Archer" -> Archer
        "Magician" -> Magician
        else -> throw IllegalArgumentException("Invalid Players File")
    }
    val personality: Personality = when(characterTypeStr){
        "Responsible" -> Responsible
        "RiskTaking" -> RiskTaking
        else -> throw IllegalArgumentException("Invalid Players File")
    }
    return Player(name=name,job=job, personality = personality)
}

