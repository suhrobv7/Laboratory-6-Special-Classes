package task08

sealed class ValidationError(val field: String, val message: String) {
    class EmptyName : ValidationError("name", "The name must not be empty")
    class InvalidAge(text: String) : ValidationError("age", "'$text' is not an age between 0 and 120")
    class TooYoung : ValidationError("age", "You must be 18 or older")
    class BadEmail(text: String) : ValidationError("email", "'$text' is not an e-mail address")

    protected constructor(code: Int) : this("code", "Error number $code")
}

fun validateFormInputs(nameStr: String, ageStr: String, emailStr: String): List<ValidationError> {
    val errors = mutableListOf<ValidationError>()
    if (nameStr.isBlank()) {
        errors.add(ValidationError.EmptyName())
    }

    val ageVal = ageStr.toIntOrNull()
    if (ageVal == null || ageVal !in 0..120) {
        errors.add(ValidationError.InvalidAge(ageStr))
    } else if (ageVal < 18) {
        errors.add(ValidationError.TooYoung())
    }

    if ('@' !in emailStr) {
        errors.add(ValidationError.BadEmail(emailStr))
    }

    return errors
}

fun main() {
    print("Name: ")
    val nameInput = readln()
    print("Age: ")
    val ageInput = readln()
    print("E-mail: ")
    val emailInput = readln()

    val formErrors = validateFormInputs(nameInput, ageInput, emailInput)
    if (formErrors.isEmpty()) {
        println("Registration accepted")
    } else {
        formErrors.forEach { println("${it.field}: ${it.message}") }
    }
}
