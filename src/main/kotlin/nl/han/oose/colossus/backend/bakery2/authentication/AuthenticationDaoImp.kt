package nl.han.oose.colossus.backend.bakery2.authentication

import org.springframework.context.annotation.Primary

@Primary
class AuthenticationDaoImp : AuthenticationDao {
//TODO: DB Connection
override fun isValidUser(email: String, password: String): Boolean {
        if ("Avisi@outlook.com" != email || "AvisiPassword" != password) {
            return false
        }
        return true
    }

    override fun tokenExists(token: String): Boolean {
        return "1234-1234-1111" == token
    }

    override fun insertToken(token: String) {
        //TODO: Insert to DB
    }
}