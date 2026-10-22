package task09

enum class Severity { MINOR, MAJOR, CRITICAL, BLOCKER }

sealed class Incident(val severity: Severity) {
    class FileProblem(val fileName: String) : Incident(Severity.MAJOR)
    class DatabaseDown(val database: String) : Incident(Severity.CRITICAL)
    class SlowPage(val url: String) : Incident(Severity.MINOR)
    object Crash : Incident(Severity.CRITICAL)
    class OutOfMemory : Incident(Severity.BLOCKER)
}

fun describeIncident(inc: Incident): String = when (inc) {
    is Incident.FileProblem -> "File ${inc.fileName} cannot be read"
    is Incident.DatabaseDown -> "Database ${inc.database} is down"
    is Incident.SlowPage -> "Page ${inc.url} is slow"
    Incident.Crash -> "The application crashed"
    is Incident.OutOfMemory -> "Out of memory error"
}

fun main() {
    val reports = mutableListOf<Incident>()
    println("Report: file NAME, db NAME, slow URL, crash, oom, or end")
    
    while (true) {
        print("> ")
        val rawParts = readln().trim().split(" ", limit = 2)
        val cmdArg = rawParts.getOrElse(1) { "" }
        
        when (rawParts[0]) {
            "file" -> reports.add(Incident.FileProblem(cmdArg))
            "db" -> reports.add(Incident.DatabaseDown(cmdArg))
            "slow" -> reports.add(Incident.SlowPage(cmdArg))
            "crash" -> reports.add(Incident.Crash)
            "oom" -> reports.add(Incident.OutOfMemory())
            "end" -> break
            else -> println("Unknown report")
        }
    }

    for (item in reports.sortedByDescending { it.severity }) {
        if (item.severity >= Severity.CRITICAL) {
            println("ALERT: High severity incident detected!")
        }
        println("[${item.severity}] ${describeIncident(item)}")
    }

    val criticalIncidents = reports.count { it.severity == Severity.CRITICAL }
    println("Critical incidents: $criticalIncidents of ${reports.size}")

    val dbList = reports.filterIsInstance<Incident.DatabaseDown>()
    if (dbList.size >= 2) {
        println("dbs[0] === dbs[1]: ${dbList[0] === dbList[1]}")
        println("dbs[0].severity === dbs[1].severity: ${dbList[0].severity === dbList[1].severity}")
    }
}
