package task13

sealed class Passenger {
    data class Adult(val name: String) : Passenger()
    data class Child(val name: String, val ageYears: Int) : Passenger()
    data class Senior(val name: String, val ageYears: Int) : Passenger()
}

fun calculateFare(person: Passenger, standardFare: Int): Int = when (person) {
    is Passenger.Child if person.ageYears < 4 -> 0
    is Passenger.Child -> standardFare / 2
    is Passenger.Senior if person.ageYears > 80 -> 0
    is Passenger.Senior -> standardFare * 7 / 10
    is Passenger.Adult -> standardFare
}

fun main() {
    print("Name: ")
    val pname = readln().trim()
    print("Age: ")
    val pageNum = readln().trim().toIntOrNull()
    
    if (pageNum == null || pageNum < 0) {
        println("Invalid age")
        return
    }

    val passObj = when {
        pageNum >= 60 -> Passenger.Senior(pname, pageNum)
        pageNum >= 18 -> Passenger.Adult(pname)
        else -> Passenger.Child(pname, pageNum)
    }

    val cost = calculateFare(passObj, 2000)
    println("$passObj pays $cost UZS")
}
