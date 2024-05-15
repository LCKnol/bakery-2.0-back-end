package nl.han.oose.colossus.backend.bakery2.pi


import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.pi.PiServiceImp
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class PiServiceTest {

    private lateinit var sut: PiService

    private lateinit var piDao: PiDao

    @BeforeEach
    fun setUp() {
        sut = PiServiceImp()
        piDao = mock(PiDao::class.java)
        sut.setPiDao(piDao)
    }

    @Test
    fun testGetPis() {
        // Arrange
        val expectedPis: PiCollectionDto = PiCollectionDto()
        `when`(piDao.getPis(1)).thenReturn(expectedPis)

        // Act
        val actualPis: PiCollectionDto = sut.getPis(1)

        // Assert
        verify(piDao).getPis(1)
        assertEquals(expectedPis, actualPis)
    }

    @Test
    fun testGetAllPis() {
        // Arrange
        val expectedPis: PiCollectionDto = PiCollectionDto()
        `when`(piDao.getAllPis()).thenReturn(expectedPis)

        // Act
        val actualPis: PiCollectionDto = sut.getAllPis()

        // Assert
        verify(piDao).getAllPis()
        assertEquals(expectedPis, actualPis)
    }


    @Test
    fun testGetAllPiRequests() {
        // Arrange
        val expectedPiRequests: PiRequestsCollectionDto = PiRequestsCollectionDto()
        `when`(piDao.getAllPiRequests()).thenReturn(expectedPiRequests)

        // Act
        val actualPiRequests: PiRequestsCollectionDto = sut.getAllPiRequests()

        // Assert
        verify(piDao).getAllPiRequests()
        assertEquals(expectedPiRequests, actualPiRequests)
    }

    @Test
    fun testAddpi() {
        // arrange
        val macAddress = "00:11:22:33:44:55"
        val name = "fake pi"
        val roomNo = "fake room"

        // act
        sut.addPi(macAddress, name, roomNo)

        // assert
        verify(piDao).insertPi(macAddress, name, roomNo)
        verify(piDao).deletePiRequest(macAddress)

    }

    @Test
    fun testDeclinePiRequest() {
        // arrange
        val macAddress = "00:11:22:33:44:55"

        // act
        sut.declinePiRequest(macAddress)

        // assert
        verify(piDao).deletePiRequest(macAddress)

    }

}
