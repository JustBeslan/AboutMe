package model

import java.time.LocalDate
import java.time.Period

data class Profile(
    var firstName: String,
    var lastName: String
) {
    var middleName: String? = null

    var dateOfBirth: LocalDate? = null
    val age: Int?
        get() =
            if (dateOfBirth == null) null
            else Period.between(dateOfBirth, LocalDate.now()).years

    var homeCountry: String? = null
    var homeCity: String? = null

    var currentCountry: String? = null
    var currentCity: String? = null

    var telephone: String? = null
    var email: String? = null
}
