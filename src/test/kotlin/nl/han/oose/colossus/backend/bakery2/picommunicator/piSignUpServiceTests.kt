package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.PiSignUpRequestDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.junit.jupiter.api.Assertions



class piSignUpServiceTests {

    private lateinit var sut: PiSignUpServiceImp

    private lateinit var piSignUpDao: PiSignUpDao

    @BeforeEach
    fun setUp() {
        this.sut = PiSignUpServiceImp()
        this.piSignUpDao = Mockito.mock(PiSignUpDao::class.java)
        sut.setPiSignUpDao(piSignUpDao)
    }

    @Test
    fun correctlyCreatesPiSignUpRequest() {
        // Arrange
        val macAddress = "macAddress"
        val ipAddress = "ipAddress"

        // Act
        sut.createSignUpRequest(macAddress, ipAddress)

        // Assert
        Mockito.verify(piSignUpDao).insertSignUpRequest(macAddress, ipAddress)
    }

    @Test
    fun checkPiExistsCallsDaoCheckPiExists() {
        // Arrange
        val piSignUpRequestDto = PiSignUpRequestDto()
        piSignUpRequestDto.setMacAddress("mac address")

        // Act
        sut.checkPiExists(piSignUpRequestDto.getMacAddress())

        // Assert
        Mockito.verify(piSignUpDao).checkPiExists(piSignUpRequestDto.getMacAddress())
    }

    @Test
    fun checkIfCheckPiSignUpExistsPassesToDao() {
        // Arrange
        val macAddress = "mac address"
        Mockito.`when`(piSignUpDao.checkPiSignUpExists(macAddress)).thenReturn(true)

        // Act
        val result = sut.checkPiSignUpExists(macAddress)

        // Assert
        Mockito.verify(piSignUpDao).checkPiSignUpExists(macAddress)
        Assertions.assertTrue(result)
    }

}