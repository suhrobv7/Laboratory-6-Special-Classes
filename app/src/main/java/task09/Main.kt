package task09

enum class Severity { MINOR, MAJOR, CRITICAL, BLOCKER }

sealed class Incident(val severity: Severity) {
    class FileProblem(val fileName: String) : Incident(Severity.MAJOR)
    class DatabaseDown(val database: String) : Incident(Severity.CRITICAL)
    class SlowPage(val url: String) : Incident(Severity.MINOR)
    object Crash : Incident(Severity.CRITICAL)
    class OutOfMemory : Incident(Severity.BLOCKER)
}

fun getIncidentDescription(inc: Incident): String = when (inc) {
    is Incident.FileProblem -> "File ${inc.fileName} cannot be read"
    is Incident.DatabaseDown -> "Database ${inc.database} is down"
    is Incident.SlowPage -> "Page ${inc.url} is slow"
    Incident.Crash -> "The application crashed"
    is Incident.OutOfMemory -> "Out of memory error"
}

fun main() {
    val logs = mutableListOf<Incident>()
    println("Report: file NAME, db NAME, slow URL, crash, oom, or end")

    while (true) {
        print("> ")
        val lineParts = readln().trim().split(" ", limit = 2)
        val argVal = lineParts.getOrElse(1) { "" }

        when (lineParts[0]) {
            "file" -> logs.add(Incident.FileProblem(argVal))
            "db" -> logs.add(Incident.DatabaseDown(argVal))
            "slow" -> logs.add(Incident.SlowPage(argVal))
            "crash" -> logs.add(Incident.Crash)
            "oom" -> logs.add(Incident.OutOfMemory())
            "end" -> break
            else -> println("Unknown report")
        }
    }

    val sortedList = logs.sortedByDescending { it.severity }
    for (item in sortedList) {
        if (item.severity >= Severity.CRITICAL) {
            println("ALERT: High severity incident detected!")
        }
        println("[${item.severity}] ${getIncidentDescription(item)}")
    }

    val criticals = logs.count { it.severity == Severity.CRITICAL }
    println("Critical incidents: $criticals of ${logs.size}")

    val dbItems = logs.filterIsInstance<Incident.DatabaseDown>()
    if (dbItems.size >= 2) {
        println("dbs[0] === dbs[1]: ${dbItems[0] === dbItems[1]}")
        println("dbs[0].severity === dbs[1].severity: ${dbItems[0].severity === dbItems[1].severity}")
    }
}
