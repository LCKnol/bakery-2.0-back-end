package nl.han.oose.colossus.backend.bakery2.authentication

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken

interface GoogleAuthService {
    fun verifyToken(idTokenString: String): GoogleIdToken.Payload
}