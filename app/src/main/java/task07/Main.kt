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

val credMap = mapOf("ali" to "1234", "dilnoza" to "qwerty")
var loggedInUser: UserData? = null

fun executeApiCall(req: ApiRequest): ApiResponse = when (req) {
    is LoginRequest -> {
        val registeredPass = credMap[req.username]
        if (registeredPass == req.password) {
            val displayName = req.username.replaceFirstChar { it.uppercase() }
            val profile = UserData("u-${req.username}", displayName)
            loggedInUser = profile
            ApiResponse.UserSuccess(profile)
        } else {
            ApiResponse.Error("Invalid username or password")
        }
    }
    LogoutRequest -> {
        val oldUser = loggedInUser
        loggedInUser = null
        if (oldUser == null) ApiResponse.NotLoggedIn else ApiResponse.UserSuccess(oldUser)
    }
    is RefreshRequest -> {
        ApiResponse.Error("Refresh is not supported yet")
    }
}

fun displayResponse(res: ApiResponse) {
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
        val rawCommand = readln().trim()
        when (rawCommand) {
            "login" -> {
                print("Username: ")
                val uname = readln().trim()
                print("Password: ")
                val pword = readln().trim()
                displayResponse(executeApiCall(LoginRequest(uname, pword)))
            }
            "logout" -> displayResponse(executeApiCall(LogoutRequest))
            "refresh" -> {
                print("Token: ")
                val tokenVal = readln().trim()
                displayResponse(executeApiCall(RefreshRequest(tokenVal)))
            }
            "exit" -> return
            else -> println("Unknown command: $rawCommand")
        }
    }
}
