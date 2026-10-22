package task11

sealed interface Chargeable { val batteryHours: Int }
sealed interface Wireless { val range: Int }
sealed interface Waterproof

data class Phone(override val batteryHours: Int = 24, override val range: Int = 50) : Chargeable, Wireless
data class Headphones(override val batteryHours: Int = 8, override val range: Int = 10) : Chargeable, Wireless, Waterproof
data class SmartLamp(override val range: Int = 30, override val batteryHours: Int = 4) : Wireless, Chargeable
class Toaster

fun makeDevice(name: String): Any? = when (name.lowercase()) {
    "phone" -> Phone()
    "headphones" -> Headphones()
    "lamp" -> SmartLamp()
    "toaster" -> Toaster()
    else -> null
}

fun chargingNote(device: Chargeable): String = when (device) {
    is Phone -> "Charge the phone every night"
    is Headphones -> "Charge the headphones after 8 hours"
    is SmartLamp -> "Charge the lamp when indicator blinks"
}

fun main() {
    val deviceList = mutableListOf<Any>()
    println("Add devices: phone, headphones, lamp, toaster (type 'done' to finish)")
    
    while (true) {
        print("> ")
        val nameInput = readln().trim()
        if (nameInput == "done") break
        
        val dev = makeDevice(nameInput)
        if (dev == null) {
            println("Unknown device")
        } else {
            deviceList.add(dev)
        }
    }

    for (dev in deviceList) {
        val specs = mutableListOf<String>()
        if (dev is Chargeable) specs.add("battery ${dev.batteryHours} h")
        if (dev is Wireless) specs.add("wireless ${dev.range} m")
        if (dev is Waterproof) specs.add("waterproof")
        
        val desc = if (specs.isEmpty()) "no special abilities" else specs.joinToString()
        println("${dev::class.simpleName}: $desc")
    }

    val chargeableCount = deviceList.count { it is Chargeable }
    println("Chargeable devices: $chargeableCount")
}
