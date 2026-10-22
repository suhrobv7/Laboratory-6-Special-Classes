package task03

enum class Delivery { PICKUP, COURIER, EXPRESS, DRONE }

fun calculateDeliveryFee(method: Delivery, weight: Double): Double = when (method) {
    Delivery.PICKUP -> 0.0
    Delivery.COURIER -> 15_000.0 + 2_000.0 * weight
    Delivery.EXPRESS -> 40_000.0 + 5_000.0 * weight
    Delivery.DRONE -> 25_000.0 + 3_000.0 * weight
}

fun main() {
    val availableTypes = Delivery.entries
    println("Delivery types: ${availableTypes.joinToString()}")
    
    print("Delivery type: ")
    val typeInput = readln().trim()
    val selectedType = availableTypes.find { it.name.equals(typeInput, ignoreCase = true) }
    
    if (selectedType == null) {
        println("Unknown delivery type")
        return
    }

    print("Weight in kg: ")
    val weightVal = readln().trim().toDoubleOrNull()
    if (weightVal == null || weightVal < 0) {
        println("Invalid weight")
        return
    }

    val totalFee = calculateDeliveryFee(selectedType, weightVal)
    println("Shipping cost: ${"%.0f".format(totalFee)} UZS")
}
