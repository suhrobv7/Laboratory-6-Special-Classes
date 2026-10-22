package task01

enum class OrderStatus { NEW, PAID, SHIPPED, DELIVERED, CANCELLED }

fun main() {
    val allStatuses = OrderStatus.entries
    println("Statuses: ${allStatuses.joinToString()}")
    print("Current status of your order: ")
    
    val inputVal = readln().trim().uppercase()

    val currentStatus = try {
        OrderStatus.valueOf(inputVal)
    } catch (err: IllegalArgumentException) {
        println("'$inputVal' is not a status. Valid: ${allStatuses.map { it.name }}")
        return
    }

    val stepNum = currentStatus.ordinal + 1
    println("You entered ${currentStatus.name}: step $stepNum of ${allStatuses.size}")
    
    val nextStatus = allStatuses.getOrNull(currentStatus.ordinal + 1)
    if (nextStatus != null) {
        println("Next step: $nextStatus")
    } else {
        println("This is the last step.")
    }

    val sameInstance = (OrderStatus.PAID === OrderStatus.valueOf("PAID"))
    println(sameInstance)
}
