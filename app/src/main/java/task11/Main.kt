package task11

sealed interface Chargeable { val batteryHours: Int }
sealed interface Wireless { val range: Int }
sealed interface Waterproof

data class Phone(override val batteryHours: Int = 24, override val range: Int = 50) : Chargeable, Wireless
data class Headphones(override val batteryHours: Int = 8, override val range: Int = 10) : Chargeable, Wireless, Waterproof
data class SmartLamp(override val range: Int = 30, override val batteryHours: Int = 4) : Wireless, Chargeable
class Toaster

fun createDeviceByName(str: String): Any? = when (str.lowercase()) {
    "phone" -> Phone()
    "headphones" -> Headphones()
    "lamp" -> SmartLamp()
    "toaster" -> Toaster()
    else -> null
}

fun getChargeNote(dev: Chargeable): String = when (dev) {
    is Phone -> "Charge the phone every night"
    is Headphones -> "Charge the headphones after 8 hours"
    is SmartLamp -> "Charge the lamp when indicator blinks"
}

fun main() {
    val devices = mutableListOf<Any>()
    println("Add devices: phone, headphones, lamp, toaster (type 'done' to finish)")

    while (true) {
        print("> ")
        val nameStr = readln().trim()
        if (nameStr == "done") break

        val created = createDeviceByName(nameStr)
        if (created != null) {
            devices.add(created)
        } else {
            println("Unknown device")
        }
    }

    for (device in devices) {
        val traits = mutableListOf<String>()
        if (device is Chargeable) traits.add("battery ${device.batteryHours} h")
        if (device is Wireless) traits.add("wireless ${device.range} m")
        if (device is Waterproof) traits.add("waterproof")

        val summary = if (traits.isEmpty()) "no special abilities" else traits.joinToString()
        println("${device::class.simpleName}: $summary")
    }

    val totalChargeable = devices.count { it is Chargeable }
    println("Chargeable devices: $totalChargeable")
}
