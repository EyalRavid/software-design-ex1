package Events

import Players.Player
import Utilities.getPotionsPurchaseMessage
import Utilities.getSolarEclipseMessage

interface SpecialEvent : Event

class SolarEclipse : SpecialEvent {
    override fun getDescription(): String {
        return "SolarEclipse"
    }

    override fun play(player: Player): String {
        return getSolarEclipseMessage(player, player.applySolarEclipse())
    }
}

class PotionsMerchant : SpecialEvent {
    override fun getDescription(): String {
        return "PotionsMerchant"
    }

    override fun play(player: Player): String {
        return getPotionsPurchaseMessage(player, player.buyPotions())
    }
}