package nl.han.oose.colossus.backend.bakery2.picommunicator


import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.pi.PiStatus
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiPingDto
import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
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
        piSignUpRequestDto.setMacAddress("macAddress")
        val pi = PiDto()
        pi.setId(1)
        pi.setDashboardId(0)
        Mockito.`when`(piService.getPi(null, piSignUpRequestDto.getMacAddress())).thenReturn(pi)
        Mockito.`when`(pisSignupService.checkPiExists(piSignUpRequestDto.getMacAddress())).thenReturn(true)
        // Act
        sut.signUpPi(piSignUpRequestDto)

        // Assert
        Mockito.verify(piService).handlePiRequest("macAddress",true)
    }

    @Test
    fun testPingPi() {
        // Arrange
        val piPingDto = PiPingDto()
        piPingDto.setMacAddress("macAddress")
        val piDto = PiDto()
        piDto.setId(1)

        Mockito.`when`(piService.getPi(null, piPingDto.getMacAddress())).thenReturn(piDto)
        Mockito.doNothing().`when`(piService).setPiStatus(PiStatus.ONLINE, piDto.getId())

        // Act
        sut.pingPi(piPingDto)

        // Assert
        Mockito.verify(piService).getPi(null, piPingDto.getMacAddress())
        Mockito.verify(piService).setPiStatus(PiStatus.ONLINE, piDto.getId())
    }
}