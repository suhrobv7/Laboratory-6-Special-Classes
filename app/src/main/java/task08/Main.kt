package task08

sealed class ValidationError(val field: String, val message: String) {
    class EmptyName : ValidationError("name", "The name must not be empty")
    class InvalidAge(text: String) : ValidationError("age", "'$text' is not an age between 0 and 120")
    class TooYoung : ValidationError("age", "You must be 18 or older")
    class BadEmail(text: String) : ValidationError("email", "'$text' is not an e-mail address")

    protected constructor(code: Int) : this("code", "Error number $code")
}

fun checkForm(nameVal: String, ageVal: String, emailVal: String): List<ValidationError> {
    val errList = mutableListOf<ValidationError>()
    if (nameVal.isBlank()) {
        errList.add(ValidationError.EmptyName())
    }
    
    val ageParsed = ageVal.toIntOrNull()
    if (ageParsed == null || ageParsed !in 0..120) {
        errList.add(ValidationError.InvalidAge(ageVal))
    } else if (ageParsed < 18) {
        errList.add(ValidationError.TooYoung())
    }

    if ('@' !in emailVal) {
        errList.add(ValidationError.BadEmail(emailVal))
    }
    
    return errList
}

fun main() {
    print("Name: ")
    val nameInput = readln()
    print("Age: ")
    val ageInput = readln()
    print("E-mail: ")
    val emailInput = readln()

    val errorsFound = checkForm(nameInput, ageInput, emailInput)
    if (errorsFound.isEmpty()) {
        println("Registration accepted")
    } else {
        for (err in errorsFound) {
            println("${err.field}: ${err.message}")
        }
    }
}
