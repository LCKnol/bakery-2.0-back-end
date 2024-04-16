package nl.han.oose.colossus.backend.bakery2.dto

class UserDto {
    var code: Int = 0
        get() = field
        private set

    var firstName: String? = null
        get() = field
        set(value) {
            field = value
        }

    var lastName: String? = null
        get() = field
        set(value) {
            field = value
        }

    var email: String? = null
        get() = field
        set(value) {
            field = value
        }

    var password: String? = null
        get() = field
        set(value) {
            field = value
        }

    var isAdmin: Boolean = false
        get() = field
        set(value) {
            field = value
        }
}
