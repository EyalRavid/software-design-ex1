package Events
import Players.Player

import java.io.Reader

interface Event {
    fun getDescription(): String
    fun play(player: Player): String
}


fun parseEvents(eventsReader : Reader) : List<Event> {
    val listOfWordsInput: List<String> = eventsReader.readLines().asSequence().flatMap { line : String -> line.split(" ", "\t").asSequence() }.filter{ word : String -> word.isNotEmpty() }.toList()
    val iterator: Iterator<String> = listOfWordsInput.iterator()
    val eventsList: MutableList<Event> = mutableListOf()

    while (iterator.hasNext()) {
        val nextEvent : Event = readNextEvent(iterator)
        eventsList.add(nextEvent)
    }

    if (eventsList.size < 2) {
        throw IllegalArgumentException("Invalid Events File")
    }
    return eventsList
}

private fun readNextEvent(iterator: Iterator<String>) : Event {
    if (!iterator.hasNext()) {
        throw IllegalArgumentException("Invalid Events File")
    }

    val wordInFile : String = iterator.next()

    val event: Event =  when (wordInFile) {
        "Snail" -> Snail()
        "Slime" -> Slime()
        "Balrog" -> Balrog()
        "Pack" -> readPack(iterator)
        "SolarEclipse" -> SolarEclipse()
        "PotionsMerchant" -> PotionsMerchant()

        else -> throw IllegalArgumentException("Invalid Events File")
    }

    return event
}

private fun readPack(iterator : Iterator<String> ): Pack {
    if (!iterator.hasNext()) {
        throw IllegalArgumentException("Invalid Events File")
    }

    val count: Int = iterator.next().toIntOrNull() ?: throw IllegalArgumentException("Invalid Events File")
    if (count < 2) {
        throw IllegalArgumentException("Invalid Events File")
    }
    val packMembers: MutableList<Monster> = mutableListOf()
    for ( i: Int in 1..count) {

        val packMember: Monster= readNextMonster(iterator)
        packMembers.add(packMember)
    }
    val pack : Pack = Pack(packMembers)
    return pack

}
private fun readNextMonster(iterator: Iterator<String>): Monster {
    if (!iterator.hasNext()) {
        throw IllegalArgumentException("Invalid Events File")
    }

    val wordInFile: String = iterator.next()

    val monster: Monster = when (wordInFile) {
        "Snail" -> Snail()
        "Slime" -> Slime()
        "Balrog" -> Balrog()
        "Pack" -> readPack(iterator)
        else -> throw IllegalArgumentException("Invalid Events File")
    }

    return monster
}