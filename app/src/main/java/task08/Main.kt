package task08

sealed class ValidationError(val field: String, val message: String) {
    class EmptyName : ValidationError("name", "The name must not be empty")
    class InvalidAge(text: String) : ValidationError("age", "'$text' is not an age between 0 and 120")
    class TooYoung : ValidationError("age", "You must be 18 or older")
    class BadEmail(text: String) : ValidationError("email", "'$text' is not an e-mail address")

    protected constructor(code: Int) : this("code", "Error number $code")
}

fun validateRegistration(name: String, age: String, email: String): List<ValidationError> {
    val issueList = mutableListOf<ValidationError>()
    if (name.isBlank()) {
        issueList.add(ValidationError.EmptyName())
    }
    
    val ageNumber = age.toIntOrNull()
    if (ageNumber == null || ageNumber !in 0..120) {
        issueList.add(ValidationError.InvalidAge(age))
    } else if (ageNumber < 18) {
        issueList.add(ValidationError.TooYoung())
    }

    if ('@' !in email) {
        issueList.add(ValidationError.BadEmail(email))
    }
    
    return issueList
}

fun main() {
    print("Name: ")
    val name = readln()
    print("Age: ")
    val age = readln()
    print("E-mail: ")
    val email = readln()

    val parsedErrors = validateRegistration(name, age, email)
    if (parsedErrors.isEmpty()) {
        println("Registration accepted")
    } else {
        parsedErrors.forEach { println("${it.field}: ${it.message}") }
    }
}
