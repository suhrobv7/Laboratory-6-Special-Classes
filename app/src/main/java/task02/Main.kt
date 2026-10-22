package task02

import kotlin.math.pow

enum class Operation(val symbol: String) {
    ADD("+") {
        override fun apply(x: Double, y: Double) = x + y
    },
    SUBTRACT("-") {
        override fun apply(x: Double, y: Double) = x - y
    },
    MULTIPLY("*") {
        override fun apply(x: Double, y: Double) = x * y
    },
    DIVIDE("/") {
        override fun apply(x: Double, y: Double) = x / y
    },
    MODULO("%") {
        override fun apply(x: Double, y: Double) = x % y
    },
    POWER("^") {
        override fun apply(x: Double, y: Double) = x.pow(y)
    };

    abstract fun apply(x: Double, y: Double): Double

    companion object {
        fun fromSymbol(sym: String): Operation? =
            entries.find { it.symbol == sym }
    }
}

fun main() {
    print("Expression (for example 12 * 4): ")
    val rawInput = readln().trim()
    val tokens = rawInput.split(" ")
    
    if (tokens.size != 3) {
        println("Use: number operator number")
        return
    }

    val num1 = tokens[0].toDoubleOrNull()
    val num2 = tokens[2].toDoubleOrNull()
    val op = Operation.fromSymbol(tokens[1])

    if (num1 == null || num2 == null || op == null) {
        println("Cannot understand '$rawInput'")
        return
    }

    if (op == Operation.DIVIDE && num2 == 0.0) {
        println("Division by zero")
        return
    }

    val result = op.apply(num1, num2)
    println("$num1 ${op.symbol} $num2 = $result")
}
