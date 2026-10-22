package task11

sealed interface Chargeable { val batteryHours: Int }
sealed interface Wireless { val range: Int }
sealed interface Waterproof

data class Phone(override val batteryHours: Int = 24, override val range: Int = 50) : Chargeable, Wireless
data class Headphones(override val batteryHours: Int = 8, override val range: Int = 10) : Chargeable, Wireless, Waterproof
data class SmartLamp(override val range: Int = 30, override val batteryHours: Int = 4) : Wireless, Chargeable
class Toaster

fun instantiateDevice(devName: String): Any? = when (devName.lowercase()) {
    "phone" -> Phone()
    "headphones" -> Headphones()
    "lamp" -> SmartLamp()
    "toaster" -> Toaster()
    else -> null
}

fun getChargingInstructions(deviceObj: Chargeable): String = when (deviceObj) {
    is Phone -> "Charge the phone every night"
    is Headphones -> "Charge the headphones after 8 hours"
    is SmartLamp -> "Charge the lamp when indicator blinks"
}

fun main() {
    val gadgetList = mutableListOf<Any>()
    println("Add devices: phone, headphones, lamp, toaster (type 'done' to finish)")
    
    while (true) {
        print("> ")
        val inputName = readln().trim()
        if (inputName == "done") break
        
        val newDev = instantiateDevice(inputName)
        if (newDev == null) {
            println("Unknown device")
        } else {
            gadgetList.add(newDev)
        }
    }

    for (item in gadgetList) {
        val traits = mutableListOf<String>()
        if (item is Chargeable) traits.add("battery ${item.batteryHours} h")
        if (item is Wireless) traits.add("wireless ${item.range} m")
        if (item is Waterproof) traits.add("waterproof")
        
        val traitSummary = if (traits.isEmpty()) "no special abilities" else traits.joinToString()
        println("${item::class.simpleName}: $traitSummary")
    }

    val chargeableTotal = gadgetList.count { it is Chargeable }
    println("Chargeable devices: $chargeableTotal")
}
