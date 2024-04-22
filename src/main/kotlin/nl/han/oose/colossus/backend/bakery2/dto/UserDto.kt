package nl.han.oose.colossus.backend.bakery2.dto

class UserDto {
    private var id: Int

    private var firstName: String

    private var lastName: String

    private var email: String

    private var password: String

    private var isAdmin: Boolean

    constructor(id: Int, firstname: String, lastname: String, email: String, password: String, isAdmin: Boolean) {
        this.id = id
        this.firstName = firstname
        this.lastName = lastname
        this.email = email
        this.password = password
        this.isAdmin = isAdmin
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun getFirstName(): String {
        return firstName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun getLastName(): String {
        return lastName
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getEmail(): String {
        return email
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getPassword(): String {
        return password
    }

    fun setIsAdmin(isAdmin: Boolean) {
        this.isAdmin = isAdmin
    }

    fun getIsAdmin(): Boolean {
        return isAdmin
    }
}
