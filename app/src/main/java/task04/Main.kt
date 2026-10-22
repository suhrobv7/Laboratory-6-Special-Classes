package task04

sealed class Payment {
    data class CreditCard(val number: String, val expiry: String) : Payment()
    data class PayPal(val email: String) : Payment()
    data object Cash : Payment()
}

fun getPaymentChoice(): Payment? {
    println("1) Credit card   2) PayPal   3) Cash")
    print("Choose a payment method: ")
    
    return when (readln().trim()) {
        "1" -> {
            print("Card number: ")
            val numStr = readln().trim()
            print("Expiry (MM/YY): ")
            val expStr = readln().trim()
            Payment.CreditCard(numStr, expStr)
        }
        "2" -> {
            print("PayPal e-mail: ")
            val emailAddr = readln().trim()
            Payment.PayPal(emailAddr)
        }
        "3" -> Payment.Cash
        else -> null
    }
}

fun processOrderPayment(paymentMethod: Payment, sum: Double) {
    when (paymentMethod) {
        is Payment.CreditCard -> {
            println("Charged $sum to the card ending ${paymentMethod.number.takeLast(4)} (expires ${paymentMethod.expiry})")
        }
        is Payment.PayPal -> {
            println("Sent a payment request for $sum to ${paymentMethod.email}")
        }
        Payment.Cash -> {
            println("Please pay $sum in cash at the desk")
        }
    }
}

fun main() {
    print("Amount to pay: ")
    val sumVal = readln().trim().toDoubleOrNull()
    if (sumVal == null) {
        println("Invalid amount")
        return
    }

    val selectedPayment = getPaymentChoice()
    if (selectedPayment == null) {
        println("Unknown payment method")
        return
    }

    println("You chose: $selectedPayment")
    processOrderPayment(selectedPayment, sumVal)
}
