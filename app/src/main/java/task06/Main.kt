package task06

sealed class UiState {
    data object Loading : UiState()
    data class Success(val city: String, val temperature: Int) : UiState()
    data class Error(val message: String) : UiState()
    data object Offline : UiState()
}

val weatherDatabase = mapOf("tashkent" to 24, "samarkand" to 22, "bukhara" to 27, "london" to 12)

fun fetchWeather(cityName: String): UiState {
    val key = cityName.lowercase()
    if (key == "offline") return UiState.Offline
    if (cityName.isBlank()) return UiState.Error("The city name is empty")
    
    val tempVal = weatherDatabase[key] ?: return UiState.Error("No data for '$cityName'")
    return UiState.Success(cityName, tempVal)
}

fun displayState(currentState: UiState) {
    when (currentState) {
        UiState.Loading -> println("Loading...")
        is UiState.Success -> println("${currentState.city}: ${currentState.temperature} C")
        is UiState.Error -> println("Error: ${currentState.message}")
        UiState.Offline -> println("No internet connection")
    }
}

fun main() {
    while (true) {
        print("City (or 'exit'): ")
        val userQuery = readln().trim()
        if (userQuery == "exit") break
        
        displayState(UiState.Loading)
        val resultState = fetchWeather(userQuery)
        displayState(resultState)
    }
}
