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

        // Act
        sut.createSignUpRequest(macAddress)

        // Assert
        Mockito.verify(piSignUpDao).insertSignUpRequest(macAddress)
    }

    @Test
    fun checkPiExistsCallsDaoCheckPiExists() {
        // Arrange
        val piSignUpRequestDto = PiSignUpRequestDto()

        // Act
        sut.checkPiExists(piSignUpRequestDto)

        // Assert
        Mockito.verify(piSignUpDao).checkPiExists(piSignUpRequestDto)
    }

}