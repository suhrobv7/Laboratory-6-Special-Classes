package task12

class QuotaExceeded(val limit: Int) : CustomError("Quota of $limit exceeded")

fun printReport(errObj: AppError) {
    val category = when (errObj) {
        is IoError -> "I/O"
        Timeout -> "timeout"
        is CustomError -> "custom"
    }
    println("[$category] ${errObj.text}")
}

fun parseError(rawLine: String): AppError? {
    val parts = rawLine.trim().split(" ", limit = 2)
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
        val userStr = readln()
        if (userStr.trim() == "end") break
        
        val createdErr = parseError(userStr)
        if (createdErr != null) {
            printReport(createdErr)
        } else {
            println("Unknown error type")
        }
    }
}
