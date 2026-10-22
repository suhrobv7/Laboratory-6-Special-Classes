package task02

import kotlin.math.pow

enum class Operation(val symbol: String) {
    ADD("+") {
        override fun apply(left: Double, right: Double) = left + right
    },
    SUBTRACT("-") {
        override fun apply(left: Double, right: Double) = left - right
    },
    MULTIPLY("*") {
        override fun apply(left: Double, right: Double) = left * right
    },
    DIVIDE("/") {
        override fun apply(left: Double, right: Double) = left / right
    },
    MODULO("%") {
        override fun apply(left: Double, right: Double) = left % right
    },
    POWER("^") {
        override fun apply(left: Double, right: Double) = left.pow(right)
    };

    abstract fun apply(left: Double, right: Double): Double

    companion object {
        fun parseSymbol(str: String): Operation? =
            entries.firstOrNull { it.symbol == str }
    }
}

fun main() {
    print("Expression (for example 12 * 4): ")
    val rawLine = readln().trim()
    val inputTokens = rawLine.split(" ")

    if (inputTokens.size != 3) {
        println("Use: number operator number")
        return
    }

    val val1 = inputTokens[0].toDoubleOrNull()
    val val2 = inputTokens[2].toDoubleOrNull()
    val mathOp = Operation.parseSymbol(inputTokens[1])

    if (val1 == null || val2 == null || mathOp == null) {
        println("Cannot understand '$rawLine'")
        return
    }

    if (mathOp == Operation.DIVIDE && val2 == 0.0) {
        println("Division by zero")
        return
    }

    val ans = mathOp.apply(val1, val2)
    println("$val1 ${mathOp.symbol} $val2 = $ans")
}
