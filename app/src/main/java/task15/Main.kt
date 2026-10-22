package task15

enum class Priority { LOW, NORMAL, HIGH, URGENT }

sealed interface Action
data class Open(val title: String, val priority: Priority) : Action
data class Close(val id: Int) : Action
data class Comment(val id: Int, val text: String) : Action
data class Reopen(val id: Int) : Action
data object ListAll : Action

class Ticket(val id: Int, val title: String, val priority: Priority) {
    var isOpen = true
    val comments = mutableListOf<String>()
}

val ticketRepository = mutableListOf<Ticket>()

fun parseActionCommand(inputLine: String): Action? {
    val tokens = inputLine.trim().split(" ", limit = 3)
    return when (tokens[0].lowercase()) {
        "open" -> {
            val prio = Priority.entries.find {
                it.name.equals(tokens.getOrNull(1), ignoreCase = true)
            }
            val titleText = tokens.getOrNull(2)
            if (prio == null || titleText.isNullOrBlank()) null else Open(titleText, prio)
        }
        "close" -> tokens.getOrNull(1)?.toIntOrNull()?.let { Close(it) }
        "reopen" -> tokens.getOrNull(1)?.toIntOrNull()?.let { Reopen(it) }
        "comment" -> {
            val ticketId = tokens.getOrNull(1)?.toIntOrNull()
            val commentText = tokens.getOrNull(2)
            if (ticketId == null || commentText.isNullOrBlank()) null else Comment(ticketId, commentText)
        }
        "list" -> ListAll
        else -> null
    }
}

fun processTicketAction(act: Action) {
    when (act) {
        is Open -> {
            val newTicket = Ticket(ticketRepository.size + 1, act.title, act.priority)
            ticketRepository.add(newTicket)
            println("Opened #${newTicket.id}: ${newTicket.title} [${newTicket.priority}]")
        }
        is Close -> {
            val target = ticketRepository.find { it.id == act.id }
            if (target == null) {
                println("There is no ticket #${act.id}")
            } else {
                target.isOpen = false
                println("Closed #${target.id}")
            }
        }
        is Reopen -> {
            val target = ticketRepository.find { it.id == act.id }
            if (target == null) {
                println("There is no ticket #${act.id}")
            } else {
                target.isOpen = true
                println("Reopened #${target.id}")
            }
        }
        is Comment -> {
            val target = ticketRepository.find { it.id == act.id }
            if (target == null) {
                println("There is no ticket #${act.id}")
            } else {
                target.comments.add(act.text)
                println("Comment added to #${target.id} (${target.comments.size} in total)")
            }
        }
        ListAll -> {
            for (item in ticketRepository.sortedByDescending { it.priority }) {
                val statusTag = if (item.isOpen) "open  " else "closed"
                println("#${item.id} $statusTag [${item.priority}] ${item.title}")
            }
            println("Open tickets: ${ticketRepository.count { it.isOpen }}")
        }
    }
}

fun main() {
    println("Commands: open PRIORITY TITLE, close ID, reopen ID, comment ID TEXT, list, exit")
    println("Priorities: ${Priority.entries.joinToString()}")
    
    while (true) {
        print("> ")
        val rawInput = readln()
        if (rawInput.trim() == "exit") break
        
        val parsedAction = parseActionCommand(rawInput)
        if (parsedAction != null) {
            processTicketAction(parsedAction)
        } else {
            println("I do not understand that")
        }
    }
}
