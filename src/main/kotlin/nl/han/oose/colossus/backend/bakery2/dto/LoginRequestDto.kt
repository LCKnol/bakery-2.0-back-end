package nl.han.oose.colossus.backend.bakery2.dto

class LoginRequestDto {
    private var email: String = null.toString()
    private var password: String = null.toString()

    fun getEmail(): String {return this.email}
    fun setEmail(email: String) {this.email = email}

    fun getPassword(): String {return this.password}
    fun setPassword(password: String) {this.password = password}



}