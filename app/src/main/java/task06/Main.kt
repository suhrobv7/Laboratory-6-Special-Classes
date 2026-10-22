package task06

sealed class UiState {
    data object Loading : UiState()
    data class Success(val city: String, val temperature: Int) : UiState()
    data class Error(val message: String) : UiState()
    data object Offline : UiState()
}

val cityData = mapOf("tashkent" to 24, "samarkand" to 22, "bukhara" to 27, "london" to 12)

fun loadCityWeather(cityName: String): UiState {
    val key = cityName.lowercase()
    if (key == "offline") return UiState.Offline
    if (cityName.isBlank()) return UiState.Error("The city name is empty")

    val temp = cityData[key] ?: return UiState.Error("No data for '$cityName'")
    return UiState.Success(cityName, temp)
}

fun renderUi(uiState: UiState) {
    when (uiState) {
        UiState.Loading -> println("Loading...")
        is UiState.Success -> println("${uiState.city}: ${uiState.temperature} C")
        is UiState.Error -> println("Error: ${uiState.message}")
        UiState.Offline -> println("No internet connection")
    }
}

fun main() {
    while (true) {
        print("City (or 'exit'): ")
        val query = readln().trim()
        if (query == "exit") break

        renderUi(UiState.Loading)
        val state = loadCityWeather(query)
        renderUi(state)
    }
}
