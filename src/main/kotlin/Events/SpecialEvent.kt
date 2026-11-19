package Events

interface SpecialEvent : Event

class SolarEclipse : SpecialEvent {
    override fun getDescription(): String {
        return "SolarEclipse"
    }
}

class PotionsMerchant : SpecialEvent {
    override fun getDescription(): String {
        return "PotionsMerchant"
    }
}