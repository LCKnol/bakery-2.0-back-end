package nl.han.oose.colossus.backend.bakery2.picommunicator


import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class piSignUpControllerTests {

    private lateinit var sut: PiSignUpController
    private lateinit var piService: PiService
    private lateinit var pisSignupService : PiSignUpService
    @Test
    @BeforeEach
    fun setUp() {
        piService = Mockito.mock(PiService::class.java)
        pisSignupService = Mockito.mock(PiSignUpService::class.java)
        sut = PiSignUpController()
        sut.setPiService(piService)
        sut.setPiSignUpService(pisSignupService)

    }

    @Test
    fun testPiSignUpCallsCreateSignUpRequest() {
        // Arrange
        val piSignUpRequestDto = PiSignUpRequestDto()

        Mockito.`when`(pisSignupService.checkPiExists(piSignUpRequestDto.getMacAddress())).thenReturn(false)
        // Act
        sut.signUpPi(piSignUpRequestDto)

        // Assert
        Mockito.verify(pisSignupService).createSignUpRequest("", "")
    }

    @Test
    fun testPiSignUpCallsHandlePiRequest() {
        // Arrange
        val piSignUpRequestDto = PiSignUpRequestDto()

        Mockito.`when`(pisSignupService.checkPiExists(piSignUpRequestDto.getMacAddress())).thenReturn(true)
        // Act
        sut.signUpPi(piSignUpRequestDto)

        // Assert
        Mockito.verify(piService).handlePiRequest("",true)
    }
}