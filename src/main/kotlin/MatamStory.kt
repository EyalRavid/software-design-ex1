import Events.Event
import Events.parseEvents
import Players.Player
import Players.parsePlayers
import Utilities.*
import java.io.Reader

class MatamStory(eventsReader: Reader, playersReader: Reader) {
    private var turnIndex: Int = 1
    private val events: List<Event> = parseEvents(eventsReader)
    private val players: List<Player> = parsePlayers(playersReader)

    private fun playTurn(player: Player) {
        val event = events[(turnIndex - 1) % events.size]
        printTurnDetails(index = turnIndex, player = player, event = event)
        val outcome: String = event.play(player)
        printTurnOutcome(outcome = outcome)
        turnIndex++
    }

    private fun playRound() {
        printRoundStart()
        for (player: Player in players) {
            if (!player.isKnockedOut()) {
                playTurn(player)
            }
        }
        printRoundEnd()
        printLeaderBoardMessage()
        val ranking: List<Player> = players.sortedWith(
            compareByDescending<Player> { player: Player -> player.getLevel() }
                .thenByDescending { player: Player -> player.getCoins() }
                .thenBy { player: Player -> player.getName() }
        )
        ranking.forEachIndexed { index: Int, player: Player -> printLeaderBoardEntry(index + 1, player) }
        printBarrier()
    }

    private fun thereIsWinner(): Boolean {
        for (player in players) {
            if (player.isAtMaxLevel()) {
                return true
            }
        }
        return false
    }

    private fun allKnockedOut(): Boolean {
        if (players.all { player: Player -> player.isKnockedOut() }) {
            return true
        }
        return false
    }


    private fun isGameOver(): Boolean {
        return allKnockedOut() || thereIsWinner()
    }

    /** Plays the entire game. The provided flow is a suggested implementation outline. */
    fun play() {
        printStartMessage()
        players.forEachIndexed { index: Int, player: Player -> printStartPlayerEntry(index + 1, player) }
        printBarrier()

        while (!isGameOver()) {
            playRound()
        }

        printGameOver()
        if (thereIsWinner()) {
            val ranking: List<Player> = players.sortedWith(
                compareByDescending<Player> { player: Player -> player.getLevel() }
                    .thenByDescending { player: Player -> player.getCoins() }
                    .thenBy { player: Player -> player.getName() }
            )
            printWinner(player = ranking[0])
        }
        else {
            printNoWinners()
        }
    }
}
