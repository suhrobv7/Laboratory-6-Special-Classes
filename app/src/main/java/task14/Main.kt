package task14

sealed interface Outcome<out T> {
    data class Ok<T>(val value: T) : Outcome<T>
    data class Fail(val reason: String) : Outcome<Nothing>
}

fun parseQuantityVal(inputStr: String): Outcome<Int> {
    val qtyVal = inputStr.trim().toIntOrNull()
        ?: return Outcome.Fail("'$inputStr' is not a whole number")
    if (qtyVal <= 0) return Outcome.Fail("The quantity must be positive")
    if (qtyVal > 100) return Outcome.Fail("We cannot sell more than 100 at once")
    return Outcome.Ok(qtyVal)
}

fun parsePriceVal(inputStr: String): Outcome<Double> {
    val priceVal = inputStr.trim().toDoubleOrNull()
        ?: return Outcome.Fail("'$inputStr' is not a price")
    if (priceVal <= 0.0) return Outcome.Fail("The price must be positive")
    return Outcome.Ok(priceVal)
}

fun parseEmailVal(inputStr: String): Outcome<String> {
    val emailStr = inputStr.trim()
    if ('@' !in emailStr) return Outcome.Fail("'$emailStr' is not a valid e-mail address")
    return Outcome.Ok(emailStr)
}

fun main() {
    print("Quantity: ")
    val qtyResult = parseQuantityVal(readln())
    print("Unit price: ")
    val priceResult = parsePriceVal(readln())
    print("E-mail: ")
    val emailResult = parseEmailVal(readln())

    when (qtyResult) {
        is Outcome.Fail -> println("Quantity error: ${qtyResult.reason}")
        is Outcome.Ok -> when (priceResult) {
            is Outcome.Fail -> println("Price error: ${priceResult.reason}")
            is Outcome.Ok -> when (emailResult) {
                is Outcome.Fail -> println("Email error: ${emailResult.reason}")
                is Outcome.Ok -> {
                    val totalCost = qtyResult.value * priceResult.value
                    println("Total: $totalCost, notification sent to ${emailResult.value}")
                }
            }
        }
    }
}
