import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDao
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationServiceImp
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.anyOrNull // Make sure to import this
import org.springframework.security.crypto.bcrypt.BCrypt
import java.util.*

class AuthenticationServiceTests {

    private lateinit var sut: AuthenticationServiceImp  // System Under Test
    private lateinit var authenticationDao: AuthenticationDao

    @BeforeEach
    fun setup() {
        sut = AuthenticationServiceImp()
        authenticationDao = Mockito.mock(AuthenticationDao::class.java)
        sut.setAuthenticationDao(authenticationDao)
    }
/*
    @Test
    fun `authenticate should return token on successful authentication`() {
        // Arrange
        val email = "Avisi@outlook.com"
        val password = "password123"
       // val hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt())
        val hashedPassword = "$2a$10"+'$'+"piwNZPAOhMhdG7Xlm/3kkOs/hZeYlfyQPAY/z7SurggdiLxfzu.KC"
        val expectedToken = UUID.randomUUID().toString()

        Mockito.`when`(authenticationDao.findPassword(email)).thenReturn(hashedPassword)
        Mockito.`when`(authenticationDao.tokenExists(anyOrNull())).thenReturn(false)
        Mockito.`when`(authenticationDao.insertToken(anyOrNull(), anyOrNull())).thenReturn(Unit)

        // Act
        val result = sut.authenticate(email, hashedPassword)

        // Assert
        assert(result.token.isNotBlank())
        verify(authenticationDao).insertToken(email, result.token)
    }

 */
}
