import Events.Event
import Events.parseEvents
import Players.Player
import Players.parsePlayers
import java.io.Reader

class MatamStory(eventsReader: Reader, playersReader: Reader) {
    private var turnIndex: Int = 1
    private val events: List<Event> = parseEvents(eventsReader)
    private val players: List<Player> = parsePlayers(playersReader)

    private fun playTurn(player: Player) {
        // TODO:
        // 1. Get the next event from the events list.
        // 2. Print the turn details using printTurnDetails.
        // 3. Play the event.
        // 4. Print the turn outcome using printTurnOutcome.
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

    private fun isGameOver(): Boolean = true //TODO("Implement the game over condition")

    /** Plays the entire game. The provided flow is a suggested implementation outline. */
    fun play() {
        printStartMessage()
        players.forEachIndexed { index: Int, player: Player -> printStartPlayerEntry(index + 1, player) }
        printBarrier()

        while (!isGameOver()) {
            playRound()
        }

        printGameOver()
        // TODO: Print either the winner using printWinner or no winner using printNoWinners.
    }
}
