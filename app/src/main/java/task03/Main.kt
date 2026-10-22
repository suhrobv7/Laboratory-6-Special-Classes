package task03

enum class Delivery { PICKUP, COURIER, EXPRESS, DRONE }

fun getShippingCost(deliveryMode: Delivery, weightInKg: Double): Double = when (deliveryMode) {
    Delivery.PICKUP -> 0.0
    Delivery.COURIER -> 15_000 + 2_000 * weightInKg
    Delivery.EXPRESS -> 40_000 + 5_000 * weightInKg
    Delivery.DRONE -> 25_000 + 3_000 * weightInKg
}

fun main() {
    println("Delivery types: ${Delivery.entries.joinToString()}")
    print("Delivery type: ")
    val optionStr = readln().trim()
    val chosenDelivery = Delivery.entries.firstOrNull { it.name.equals(optionStr, ignoreCase = true) }
    
    if (chosenDelivery == null) {
        println("Unknown delivery type")
        return
    }

    print("Weight in kg: ")
    val inputWeight = readln().trim().toDoubleOrNull()
    if (inputWeight == null || inputWeight < 0) {
        println("Invalid weight")
        return
    }

    val costVal = getShippingCost(chosenDelivery, inputWeight)
    println("Shipping cost: ${"%.0f".format(costVal)} UZS")
}
