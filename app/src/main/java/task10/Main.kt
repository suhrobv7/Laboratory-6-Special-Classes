package task10

sealed interface PlayerCommand

enum class Basic : PlayerCommand { PLAY, PAUSE, STOP, MUTE }
data class Seek(val seconds: Int) : PlayerCommand
data class Volume(val level: Int) : PlayerCommand

fun parsePlayerCommand(str: String): PlayerCommand? {
    val tokens = str.trim().lowercase().split(" ")
    val numParam = tokens.getOrNull(1)?.toIntOrNull()
    
    return when (tokens[0]) {
        "play" -> Basic.PLAY
        "pause" -> Basic.PAUSE
        "stop" -> Basic.STOP
        "mute" -> Basic.MUTE
        "seek" -> numParam?.let { Seek(it) }
        "volume" -> numParam?.takeIf { it in 0..100 }?.let { Volume(it) }
        else -> null
    }
}

class MediaPlayer {
    private var trackPos = 0
    private var soundLevel = 50

    fun processCommand(cmd: PlayerCommand) {
        when (cmd) {
            Basic.PLAY -> println("Playing from ${trackPos}s")
            Basic.PAUSE -> println("Paused at ${trackPos}s")
            Basic.STOP -> {
                trackPos = 0
                println("Stopped")
            }
            Basic.MUTE -> println("Muted")
            is Seek -> {
                trackPos = cmd.seconds
                println("Jumped to ${trackPos}s")
            }
            is Volume -> {
                soundLevel = cmd.level
                println("Volume is now $soundLevel%")
            }
        }
    }
}

fun main() {
    val player = MediaPlayer()
    println("Commands: play, pause, stop, mute, seek N, volume N (0-100), quit")
    
    while (true) {
        print("> ")
        val line = readln()
        if (line.trim() == "quit") break
        
        val cmd = parsePlayerCommand(line)
        if (cmd == null) {
            println("Unknown command")
        } else {
            player.processCommand(cmd)
        }
    }
}
