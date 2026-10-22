package task10

sealed interface PlayerCommand

enum class Basic : PlayerCommand { PLAY, PAUSE, STOP, MUTE }
data class Seek(val seconds: Int) : PlayerCommand
data class Volume(val level: Int) : PlayerCommand

fun parsePlayerCommand(str: String): PlayerCommand? {
    val tokens = str.trim().lowercase().split(" ")
    val param = tokens.getOrNull(1)?.toIntOrNull()

    return when (tokens[0]) {
        "play" -> Basic.PLAY
        "pause" -> Basic.PAUSE
        "stop" -> Basic.STOP
        "mute" -> Basic.MUTE
        "seek" -> param?.let { Seek(it) }
        "volume" -> param?.takeIf { it in 0..100 }?.let { Volume(it) }
        else -> null
    }
}

class MediaPlayer {
    private var position = 0
    private var volume = 50

    fun processCommand(cmd: PlayerCommand) {
        when (cmd) {
            Basic.PLAY -> println("Playing from ${position}s")
            Basic.PAUSE -> println("Paused at ${position}s")
            Basic.STOP -> {
                position = 0
                println("Stopped")
            }
            Basic.MUTE -> println("Muted")
            is Seek -> {
                position = cmd.seconds
                println("Jumped to ${position}s")
            }
            is Volume -> {
                volume = cmd.level
                println("Volume is now $volume%")
            }
        }
    }
}

fun main() {
    val player = MediaPlayer()
    println("Commands: play, pause, stop, mute, seek N, volume N (0-100), quit")

    while (true) {
        print("> ")
        val input = readln()
        if (input.trim() == "quit") break

        val command = parsePlayerCommand(input)
        if (command != null) {
            player.processCommand(command)
        } else {
            println("Unknown command")
        }
    }
}
