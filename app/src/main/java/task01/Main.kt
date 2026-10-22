package task01

enum class OrderStatus { NEW, PAID, SHIPPED, DELIVERED, CANCELLED }

fun main() {
    val statusEntries = OrderStatus.entries
    println("Statuses: ${statusEntries.joinToString()}")
    print("Current status of your order: ")
    
    val rawText = readln().trim()
    val upperText = rawText.uppercase()

    val currentEnum = try {
        OrderStatus.valueOf(upperText)
    } catch (_: IllegalArgumentException) {
        println("'$upperText' is not a status. Valid: ${statusEntries.map { it.name }}")
        return
    }

    val stepIndex = currentEnum.ordinal + 1
    println("You entered ${currentEnum.name}: step $stepIndex of ${statusEntries.size}")

    val nextStep = statusEntries.getOrNull(currentEnum.ordinal + 1)
    if (nextStep != null) {
        println("Next step: $nextStep")
    } else {
        println("This is the last step.")
    }

    println(OrderStatus.PAID === OrderStatus.valueOf("PAID"))
}
