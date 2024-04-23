import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDao
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationServiceImp
import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.exceptions.HttpUnauthorizedException
import nl.han.oose.colossus.backend.bakery2.token.HeaderService
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.security.crypto.bcrypt.BCrypt
import java.util.*

class AuthenticationServiceTests {

    private lateinit var sut: AuthenticationServiceImp  // System Under Test
    private lateinit var authenticationDao: AuthenticationDao

    @BeforeEach
    fun setup() {

        sut = AuthenticationServiceImp()
        authenticationDao = mock(AuthenticationDao::class.java)
        sut.setAuthenticationDao(authenticationDao)
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

        `when`(authenticationDao.findPassword(email)).thenReturn(storedHash)
        doNothing().`when`(authenticationDao).insertToken(MockitoHelper.anyObject(), MockitoHelper.anyObject())

        // Act
        val result = sut.authenticate(email, password)

        // Assert
        Assertions.assertNotNull(result.token)
        verify(authenticationDao).findPassword(email)
        verify(authenticationDao).insertToken(MockitoHelper.anyObject(), MockitoHelper.anyObject())
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

//        @Test
//        fun registerUserSucceed() {
//            // Arrange
//            val userDto = UserDto(
//                id = 1,
//                firstname = "reem",
//                lastname = "man",
//                email = "reem.@gmail.com",
//                password = "mypassword",
//                isAdmin = true
//            )
//
//            val userPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt())
//            userDto.setPassword(userPassword)
//
//            doNothing().`when`(authenticationDao).insertUser(userDto)
//            // Act
//            sut.registerUser(userDto)
//
//            // Assert
//            verify(authenticationDao).insertUser(userDto)
//        }




    }


