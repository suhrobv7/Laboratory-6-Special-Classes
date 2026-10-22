package task01

enum class OrderStatus { NEW, PAID, SHIPPED, DELIVERED, CANCELLED }

fun main() {
    val statuses = OrderStatus.entries
    println("Statuses: ${statuses.joinToString()}")
    print("Current status of your order: ")
    
    val enteredText = readln().trim().uppercase()

    val parsedStatus = try {
        OrderStatus.valueOf(enteredText)
    } catch (e: IllegalArgumentException) {
        println("'$enteredText' is not a status. Valid: ${statuses.map { it.name }}")
        return
    }

    val positionIndex = parsedStatus.ordinal + 1
    println("You entered ${parsedStatus.name}: step $positionIndex of ${statuses.size}")
    
    val hasNext = statuses.getOrNull(parsedStatus.ordinal + 1)
    println(if (hasNext == null) "This is the last step." else "Next step: $hasNext")

    println(OrderStatus.PAID === OrderStatus.valueOf("PAID"))
}
