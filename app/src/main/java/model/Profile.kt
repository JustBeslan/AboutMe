package model

import java.time.LocalDate
import java.time.Period

enum class Gender { MALE, FEMALE }

object Profile {
    var firstName: String? = null
    var lastName: String? = null
    var patronymic: String? = null

    var gender: Gender? = null

    var dateOfBirth: LocalDate? = null
    val age: Int?
        get() =
            if (dateOfBirth == null) null
            else Period.between(dateOfBirth, LocalDate.now()).years

    var currentCountry: String? = null
    var currentState: String? = null
    var currentCity: String? = null

    var homeCountry: String? = null
    var homeState: String? = null
    var homeCity: String? = null

    var telephone: String? = null
    var email: String? = null
}
