package nl.han.oose.colossus.backend.bakery2.Pi


import junit.framework.Assert.assertEquals
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.pi.PiServiceImp
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class PiServiceTest {

    private lateinit var sut: PiService

    private lateinit var piDao: PiDao

    @Test
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

    }
