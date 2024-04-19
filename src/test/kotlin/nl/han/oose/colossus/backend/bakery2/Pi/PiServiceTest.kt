package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsServiceImp
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class PiServiceTest {

    private lateinit var sut: PiService

    private lateinit var piDao: PiDao

    @Test
    @BeforeEach
    fun setUp() {
        sut = PiServiceImp()
        piDao = Mockito.mock(PiDao::class.java)
        sut.setPiDao(piDao)
    }

    @Test
    fun testGetPis() {
        // Arrange
        `when`(piDao.getPis(1)).thenReturn(PiCollectionDto())
        // Act
        sut.getPis(1)

        // Assert
        Mockito.verify(piDao).getPis(1)
    }
    }
