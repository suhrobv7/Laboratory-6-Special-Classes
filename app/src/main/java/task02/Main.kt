package task02

import kotlin.math.pow

enum class Operation(val symbol: String) {
    ADD("+") {
        override fun apply(first: Double, second: Double) = first + second
    },
    SUBTRACT("-") {
        override fun apply(first: Double, second: Double) = first - second
    },
    MULTIPLY("*") {
        override fun apply(first: Double, second: Double) = first * second
    },
    DIVIDE("/") {
        override fun apply(first: Double, second: Double) = first / second
    },
    MODULO("%") {
        override fun apply(first: Double, second: Double) = first % second
    },
    POWER("^") {
        override fun apply(first: Double, second: Double) = first.pow(second)
    };

    abstract fun apply(first: Double, second: Double): Double

    companion object {
        fun findOperationBySymbol(sym: String): Operation? =
            entries.firstOrNull { it.symbol == sym }
    }
}

fun main() {
    print("Expression (for example 12 * 4): ")
    val inputParts = readln().trim().split(" ")
    
    if (inputParts.size != 3) {
        println("Use: number operator number")
        return
    }

    val operand1 = inputParts[0].toDoubleOrNull()
    val operand2 = inputParts[2].toDoubleOrNull()
    val selectedOp = Operation.findOperationBySymbol(inputParts[1])

    if (operand1 == null || operand2 == null || selectedOp == null) {
        println("Cannot understand '${inputParts.joinToString(" ")}'")
        return
    }

    if (selectedOp == Operation.DIVIDE && operand2 == 0.0) {
        println("Division by zero")
        return
    }

    val calcResult = selectedOp.apply(operand1, operand2)
    println("$operand1 ${selectedOp.symbol} $operand2 = $calcResult")
}
