package Players

const val POTION_COST = 5
const val POTION_HEAL = 10
private const val RISK_TAKING_HP_THRESHOLD = 50
private const val RISK_TAKER_BUY_AMOUNT = 1

interface Personality {
    val personalityName: String
    fun potionsToBuy(hp: Int, maxHp: Int, coins: Int): Int
}

object Responsible : Personality {
    override val personalityName = "Responsible"
    override fun potionsToBuy(hp: Int, maxHp: Int, coins: Int): Int {
        val affordable = coins / POTION_COST
        val needed = (maxHp - hp + POTION_HEAL - 1) / POTION_HEAL // ceil of missing HP / POTION_HEAL
        return minOf(affordable, needed)
    }
}

object RiskTaking : Personality {
    override val personalityName = "RiskTaking"
    override fun potionsToBuy(hp: Int, maxHp: Int, coins: Int): Int =
        if (hp < RISK_TAKING_HP_THRESHOLD && coins >= POTION_COST) RISK_TAKER_BUY_AMOUNT else 0
}
