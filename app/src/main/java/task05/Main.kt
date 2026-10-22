package task05

sealed class Payment {
    data class CreditCard(val number: String, val expiry: String) : Payment()
    data class PayPal(val email: String) : Payment()
    data object Cash : Payment()
    data class Crypto(val wallet: String) : Payment()
}

fun readPaymentDetails(): Payment? {
    println("1) Credit card   2) PayPal   3) Cash   4) Crypto")
    print("Choose a payment method: ")

    return when (readln().trim()) {
        "1" -> {
            print("Card number: ")
            val cardNumber = readln().trim()
            print("Expiry (MM/YY): ")
            val expDate = readln().trim()
            Payment.CreditCard(cardNumber, expDate)
        }
        "2" -> {
            print("PayPal e-mail: ")
            val paypalEmail = readln().trim()
            Payment.PayPal(paypalEmail)
        }
        "3" -> Payment.Cash
        "4" -> {
            print("Wallet address: ")
            val cryptoAddress = readln().trim()
            Payment.Crypto(cryptoAddress)
        }
        else -> null
    }
}

fun processPaymentRequest(p: Payment, amt: Double) {
    when (p) {
        is Payment.CreditCard -> {
            println("Charged $amt to the card ending ${p.number.takeLast(4)} (expires ${p.expiry})")
        }
        is Payment.PayPal -> {
            println("Sent a payment request for $amt to ${p.email}")
        }
        Payment.Cash -> {
            println("Please pay $amt in cash at the desk")
        }
        is Payment.Crypto -> {
            println("Waiting for a transfer to wallet ${p.wallet}")
        }
    }
}

fun main() {
    print("Amount to pay: ")
    val amtVal = readln().trim().toDoubleOrNull()
    if (amtVal == null) {
        println("Invalid amount")
        return
    }

    val selectedPayment = readPaymentDetails()
    if (selectedPayment == null) {
        println("Unknown payment method")
        return
    }

    println("You chose: $selectedPayment")
    processPaymentRequest(selectedPayment, amtVal)
}
