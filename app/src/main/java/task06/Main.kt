package task06

sealed class UiState {
    data object Loading : UiState()
    data class Success(val city: String, val temperature: Int) : UiState()
    data class Error(val message: String) : UiState()
    data object Offline : UiState()
}

val cityTempMap = mapOf("tashkent" to 24, "samarkand" to 22, "bukhara" to 27, "london" to 12)

fun getCityWeather(cityName: String): UiState {
    val searchKey = cityName.lowercase()
    if (searchKey == "offline") return UiState.Offline
    if (cityName.isBlank()) return UiState.Error("The city name is empty")
    
    val temp = cityTempMap[searchKey] ?: return UiState.Error("No data for '$cityName'")
    return UiState.Success(cityName, temp)
}

fun renderScreen(screenState: UiState) {
    when (screenState) {
        UiState.Loading -> println("Loading...")
        is UiState.Success -> println("${screenState.city}: ${screenState.temperature} C")
        is UiState.Error -> println("Error: ${screenState.message}")
        UiState.Offline -> println("No internet connection")
    }
}

fun main() {
    while (true) {
        print("City (or 'exit'): ")
        val inputCity = readln().trim()
        if (inputCity == "exit") break
        
        renderScreen(UiState.Loading)
        val state = getCityWeather(inputCity)
        renderScreen(state)
    }
}
