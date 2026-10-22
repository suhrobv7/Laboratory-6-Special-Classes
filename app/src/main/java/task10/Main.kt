package task10

sealed interface PlayerCommand

enum class Basic : PlayerCommand { PLAY, PAUSE, STOP, MUTE }
data class Seek(val seconds: Int) : PlayerCommand
data class Volume(val level: Int) : PlayerCommand

fun parseCommand(lineStr: String): PlayerCommand? {
    val words = lineStr.trim().lowercase().split(" ")
    val valArg = words.getOrNull(1)?.toIntOrNull()
    
    return when (words[0]) {
        "play" -> Basic.PLAY
        "pause" -> Basic.PAUSE
        "stop" -> Basic.STOP
        "mute" -> Basic.MUTE
        "seek" -> valArg?.let { Seek(it) }
        "volume" -> valArg?.takeIf { it in 0..100 }?.let { Volume(it) }
        else -> null
    }
}

class AudioPlayer {
    private var currPos = 0
    private var volumeLevel = 50

    fun executeCmd(cmd: PlayerCommand) {
        when (cmd) {
            Basic.PLAY -> println("Playing from ${currPos}s")
            Basic.PAUSE -> println("Paused at ${currPos}s")
            Basic.STOP -> {
                currPos = 0
                println("Stopped")
            }
            Basic.MUTE -> println("Muted")
            is Seek -> {
                currPos = cmd.seconds
                println("Jumped to ${currPos}s")
            }
            is Volume -> {
                volumeLevel = cmd.level
                println("Volume is now $volumeLevel%")
            }
        }
    }
}

fun main() {
    val playerInst = AudioPlayer()
    println("Commands: play, pause, stop, mute, seek N, volume N (0-100), quit")
    
    while (true) {
        print("> ")
        val userInput = readln()
        if (userInput.trim() == "quit") break
        
        val parsedCmd = parseCommand(userInput)
        if (parsedCmd != null) {
            playerInst.executeCmd(parsedCmd)
        } else {
            println("Unknown command")
        }
    }
}
