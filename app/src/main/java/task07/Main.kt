package task07

sealed interface ApiRequest
data class LoginRequest(val username: String, val password: String) : ApiRequest
data object LogoutRequest : ApiRequest
data class RefreshRequest(val token: String) : ApiRequest

data class UserData(val userId: String, val name: String)

sealed class ApiResponse {
    data class UserSuccess(val user: UserData) : ApiResponse()
    data object NotLoggedIn : ApiResponse()
    data class Error(val message: String) : ApiResponse()
}

val accountsDb = mapOf("ali" to "1234", "dilnoza" to "qwerty")
var activeUser: UserData? = null

fun handleApiRequest(req: ApiRequest): ApiResponse = when (req) {
    is LoginRequest -> {
        if (accountsDb[req.username] == req.password) {
            val formattedName = req.username.replaceFirstChar { it.uppercase() }
            val user = UserData("u-${req.username}", formattedName)
            activeUser = user
            ApiResponse.UserSuccess(user)
        } else {
            ApiResponse.Error("Invalid username or password")
        }
    }
    LogoutRequest -> {
        val user = activeUser
        activeUser = null
        if (user == null) ApiResponse.NotLoggedIn else ApiResponse.UserSuccess(user)
    }
    is RefreshRequest -> {
        ApiResponse.Error("Refresh is not supported yet")
    }
}

fun printResponse(res: ApiResponse) {
    when (res) {
        is ApiResponse.UserSuccess -> println("OK: ${res.user.name} (${res.user.userId})")
        ApiResponse.NotLoggedIn -> println("Nobody is logged in")
        is ApiResponse.Error -> println("FAILED: ${res.message}")
    }
}

fun main() {
    println("Commands: login, logout, refresh, exit")
    while (true) {
        print("> ")
        when (val cmdStr = readln().trim()) {
            "login" -> {
                print("Username: ")
                val userStr = readln().trim()
                print("Password: ")
                val passStr = readln().trim()
                printResponse(handleApiRequest(LoginRequest(userStr, passStr)))
            }
            "logout" -> printResponse(handleApiRequest(LogoutRequest))
            "refresh" -> {
                print("Token: ")
                val tokStr = readln().trim()
                printResponse(handleApiRequest(RefreshRequest(tokStr)))
            }
            "exit" -> return
            else -> println("Unknown command: $cmdStr")
        }
    }
}
