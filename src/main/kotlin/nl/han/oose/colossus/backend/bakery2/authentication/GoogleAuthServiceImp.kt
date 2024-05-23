package nl.han.oose.colossus.backend.bakery2.authentication

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import kotlin.IllegalArgumentException
import kotlin.String

@Primary
@Component
class GoogleAuthServiceImp : GoogleAuthService {

    //CONFIG
    private val clientID = "459811137064-cfhur48n8304qbm0t55hg9c06rtbl9l0.apps.googleusercontent.com"
    private val authorizedDomain = null


    private val transport: HttpTransport = NetHttpTransport()
    private val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()


    override fun verifyToken(idTokenString: String): GoogleIdToken.Payload {

        try {
            val verifier = GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(listOf<String>(clientID))
                .build()


            val idToken: GoogleIdToken? = verifier.verify(idTokenString)
            if (idToken == null || idToken.payload.hostedDomain != authorizedDomain) {
                throw HttpUnauthorizedException("Invalid ID token")
            }
            println(idToken.payload.hostedDomain)
            return idToken.payload

        } catch (e: IllegalArgumentException) {

            throw HttpUnauthorizedException("Oops! something went wrong")
        }

    }


}