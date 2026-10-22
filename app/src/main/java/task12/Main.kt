package task12

class QuotaExceeded(val limit: Int) : CustomError("Quota of $limit exceeded")

fun logError(err: AppError) {
    val errCategory = when (err) {
        is IoError -> "I/O"
        Timeout -> "timeout"
        is CustomError -> "custom"
    }
    println("[$errCategory] ${err.text}")
}

fun parseAppError(input: String): AppError? {
    val parts = input.trim().split(" ", limit = 2)
    val arg = parts.getOrElse(1) { "" }
    
    return when (parts[0]) {
        "missing" -> IoError.FileMissing(arg)
        "denied" -> IoError.NoPermission(arg)
        "disk" -> DiskFull(arg.toIntOrNull() ?: 0)
        "timeout" -> Timeout
        "quota" -> QuotaExceeded(arg.toIntOrNull() ?: 0)
        "other" -> CustomError(arg)
        else -> null
    }
}

fun main() {
    println("Describe an error: missing PATH, denied PATH, disk MB, timeout, quota N, other TEXT, or end")
    while (true) {
        print("> ")
        val input = readln()
        if (input.trim() == "end") break
        
        val errorObj = parseAppError(input)
        if (errorObj == null) {
            println("Unknown error type")
        } else {
            logError(errorObj)
        }
    }
}
