package task09

enum class Severity { MINOR, MAJOR, CRITICAL, BLOCKER }

sealed class Incident(val severity: Severity) {
    class FileProblem(val fileName: String) : Incident(Severity.MAJOR)
    class DatabaseDown(val database: String) : Incident(Severity.CRITICAL)
    class SlowPage(val url: String) : Incident(Severity.MINOR)
    object Crash : Incident(Severity.CRITICAL)
    class OutOfMemory : Incident(Severity.BLOCKER)
}

fun getIncidentDetails(item: Incident): String = when (item) {
    is Incident.FileProblem -> "File ${item.fileName} cannot be read"
    is Incident.DatabaseDown -> "Database ${item.database} is down"
    is Incident.SlowPage -> "Page ${item.url} is slow"
    Incident.Crash -> "The application crashed"
    is Incident.OutOfMemory -> "Out of memory error"
}

fun main() {
    val incidentLog = mutableListOf<Incident>()
    println("Report: file NAME, db NAME, slow URL, crash, oom, or end")
    
    while (true) {
        print("> ")
        val rawLine = readln().trim()
        val tokens = rawLine.split(" ", limit = 2)
        val param = tokens.getOrElse(1) { "" }
        
        when (tokens[0]) {
            "file" -> incidentLog.add(Incident.FileProblem(param))
            "db" -> incidentLog.add(Incident.DatabaseDown(param))
            "slow" -> incidentLog.add(Incident.SlowPage(param))
            "crash" -> incidentLog.add(Incident.Crash)
            "oom" -> incidentLog.add(Incident.OutOfMemory())
            "end" -> break
            else -> println("Unknown report")
        }
    }

    val sortedIncidents = incidentLog.sortedByDescending { it.severity }
    for (entry in sortedIncidents) {
        if (entry.severity >= Severity.CRITICAL) {
            println("ALERT: High severity incident detected!")
        }
        println("[${entry.severity}] ${getIncidentDetails(entry)}")
    }

    val totalCritical = incidentLog.count { it.severity == Severity.CRITICAL }
    println("Critical incidents: $totalCritical of ${incidentLog.size}")

    val dbItems = incidentLog.filterIsInstance<Incident.DatabaseDown>()
    if (dbItems.size >= 2) {
        println("dbs[0] === dbs[1]: ${dbItems[0] === dbItems[1]}")
        println("dbs[0].severity === dbs[1].severity: ${dbItems[0].severity === dbItems[1].severity}")
    }
}
