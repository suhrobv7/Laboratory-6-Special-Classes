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

val userAccounts = mapOf("ali" to "1234", "dilnoza" to "qwerty")
var activeUser: UserData? = null

fun processRequest(req: ApiRequest): ApiResponse = when (req) {
    is LoginRequest -> {
        val expectedPass = userAccounts[req.username]
        if (expectedPass != null && expectedPass == req.password) {
            val formattedName = req.username.replaceFirstChar { it.uppercase() }
            val profile = UserData("u-${req.username}", formattedName)
            activeUser = profile
            ApiResponse.UserSuccess(profile)
        } else {
            ApiResponse.Error("Invalid username or password")
        }
    }
    LogoutRequest -> {
        val prevUser = activeUser
        activeUser = null
        if (prevUser == null) ApiResponse.NotLoggedIn else ApiResponse.UserSuccess(prevUser)
    }
    is RefreshRequest -> {
        ApiResponse.Error("Refresh is not supported yet")
    }
}

fun renderResponse(res: ApiResponse) {
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
        val command = readln().trim()
        when (command) {
            "login" -> {
                print("Username: ")
                val uname = readln().trim()
                print("Password: ")
                val pass = readln().trim()
                renderResponse(processRequest(LoginRequest(uname, pass)))
            }
            "logout" -> renderResponse(processRequest(LogoutRequest))
            "refresh" -> {
                print("Token: ")
                val tok = readln().trim()
                renderResponse(processRequest(RefreshRequest(tok)))
            }
            "exit" -> return
            else -> println("Unknown command: $command")
        }
    }
}
