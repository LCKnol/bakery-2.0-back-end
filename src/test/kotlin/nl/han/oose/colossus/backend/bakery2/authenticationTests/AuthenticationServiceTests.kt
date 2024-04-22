import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDao
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationServiceImp
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import nl.han.oose.colossus.backend.bakery2.token.TokenService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.springframework.security.crypto.bcrypt.BCrypt
import java.util.*

class AuthenticationServiceTests {

    private lateinit var sut: AuthenticationServiceImp  // System Under Test
    private lateinit var authenticationDao: AuthenticationDao
    private lateinit var tokenService: TokenService

    @BeforeEach
    fun setup() {

        sut = AuthenticationServiceImp()
        authenticationDao = mock(AuthenticationDao::class.java)
        tokenService = mock(TokenService::class.java)
        sut.setAuthenticationDao(authenticationDao)
        sut.setTokenService(tokenService)
        }


    @Test
    fun authenticateThrowsHttpUnauthorizedExceptionIfPasswordIsIncorrect() {
        // Arrange
        val email = "user@example.com"
        val password = "wrongPassword"
        val storedHash = BCrypt.hashpw("correctPassword", BCrypt.gensalt())

        `when`(authenticationDao.findPassword(email)).thenReturn(storedHash)

        // Act & Assert
        assertThrows<HttpUnauthorizedException> {
            sut.authenticate(email, password)
        }
    }


    @Test
    fun authenticateReturnsValidTokenIfCredentialsCorrect() {
        // Arrange
        val email = "user@example.com"
        val password = "correctPassword"
        val storedHash = BCrypt.hashpw(password, BCrypt.gensalt())
        val token = "newToken"

        `when`(authenticationDao.findPassword(email)).thenReturn(storedHash)
        `when`(tokenService.generateToken()).thenReturn(token)

        // Act
        val result = sut.authenticate(email, password)

        // Assert
        assert(result.token == token)
    }


    @Test
        fun testDestroySession() {
            // Arrange
            val token = "testToken123"

            // Act
            sut.destroySession(token)

            // Assert
            verify(authenticationDao).deleteSession(token)
        }

        @Test
        fun validateTokenThrowHttpUnauthorizedExceptionIfTokenDoesNotExist() {
            // Arrange
            val token = "nonExistentToken"
            `when`(authenticationDao.tokenExists(token)).thenReturn(false)

            // Act & Assert
            assertThrows<HttpUnauthorizedException> {
                sut.validateToken(token)
            }
        }

        @Test
        fun validateTokenSucceed() {
            // Arrange
            val token = "existingToken"
            `when`(authenticationDao.tokenExists(token)).thenReturn(true)

            // Act
            sut.validateToken(token)

            // Assert
            verify(authenticationDao).tokenExists(token)
        }




    }


