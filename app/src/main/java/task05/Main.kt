package task05

sealed class Payment {
    data class CreditCard(val number: String, val expiry: String) : Payment()
    data class PayPal(val email: String) : Payment()
    data object Cash : Payment()
    data class Crypto(val wallet: String) : Payment()
}

fun promptPaymentMethod(): Payment? {
    println("1) Credit card   2) PayPal   3) Cash   4) Crypto")
    print("Choose a payment method: ")
    
    return when (readln().trim()) {
        "1" -> {
            print("Card number: ")
            val cardNum = readln().trim()
            print("Expiry (MM/YY): ")
            val cardExp = readln().trim()
            Payment.CreditCard(cardNum, cardExp)
        }
        "2" -> {
            print("PayPal e-mail: ")
            val payEmail = readln().trim()
            Payment.PayPal(payEmail)
        }
        "3" -> Payment.Cash
        "4" -> {
            print("Wallet address: ")
            val walletAddr = readln().trim()
            Payment.Crypto(walletAddr)
        }
        else -> null
    }
}

fun executePayment(selectedMethod: Payment, totalAmount: Double) {
    when (selectedMethod) {
        is Payment.CreditCard -> {
            val lastFour = selectedMethod.number.takeLast(4)
            println("Charged $totalAmount to the card ending $lastFour (expires ${selectedMethod.expiry})")
        }
        is Payment.PayPal -> {
            println("Sent a payment request for $totalAmount to ${selectedMethod.email}")
        }
        Payment.Cash -> {
            println("Please pay $totalAmount in cash at the desk")
        }
        is Payment.Crypto -> {
            println("Waiting for a transfer to wallet ${selectedMethod.wallet}")
        }
    }
}

fun main() {
    print("Amount to pay: ")
    val amountVal = readln().trim().toDoubleOrNull()
    if (amountVal == null) {
        println("Invalid amount")
        return
    }

    val chosenPayment = promptPaymentMethod()
    if (chosenPayment == null) {
        println("Unknown payment method")
        return
    }

    println("You chose: $chosenPayment")
    executePayment(chosenPayment, amountVal)
}
