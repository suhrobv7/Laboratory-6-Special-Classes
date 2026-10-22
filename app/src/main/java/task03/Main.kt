package task03

enum class Delivery { PICKUP, COURIER, EXPRESS, DRONE }

fun computeShipping(delType: Delivery, weight: Double): Double = when (delType) {
    Delivery.PICKUP -> 0.0
    Delivery.COURIER -> 15_000 + 2_000 * weight
    Delivery.EXPRESS -> 40_000 + 5_000 * weight
    Delivery.DRONE -> 25_000 + 3_000 * weight
}

fun main() {
    val listTypes = Delivery.entries
    println("Delivery types: ${listTypes.joinToString()}")
    
    print("Delivery type: ")
    val delInput = readln().trim()
    val matchType = listTypes.firstOrNull { it.name.equals(delInput, ignoreCase = true) }

    if (matchType == null) {
        println("Unknown delivery type")
        return
    }

    print("Weight in kg: ")
    val wKg = readln().trim().toDoubleOrNull()
    if (wKg == null || wKg < 0) {
        println("Invalid weight")
        return
    }

    val finalPrice = computeShipping(matchType, wKg)
    println("Shipping cost: ${"%.0f".format(finalPrice)} UZS")
}
