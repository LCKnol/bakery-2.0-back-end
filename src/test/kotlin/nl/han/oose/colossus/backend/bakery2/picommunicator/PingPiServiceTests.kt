package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiDto
import nl.han.oose.colossus.backend.bakery2.pi.PiService
import nl.han.oose.colossus.backend.bakery2.pi.PiStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class PingPisServiceTests {
    private lateinit var sut: PingPiService

    private lateinit var piService: PiService

    @BeforeEach
    fun setUp() {
        this.sut = PingPiService()
        this.piService = Mockito.mock(PiService::class.java)
        sut.setPiService(piService)
    }

    @Test
    fun checkPingAllPisWorksCorrectly() {
        // Arrange
        val piCollection = PiCollectionDto()
        val pi = PiDto()
        pi.setId(1)
        val list = ArrayList<PiDto>()
        list.add(pi)
        piCollection.setPis(list)
        Mockito.`when`(piService.getAllPis()).thenReturn(piCollection)
        Mockito.doNothing().`when`(piService).pingPi(1)
        Mockito.doNothing().`when`(piService).setPiStatus(PiStatus.OFFLINE, 1)

        // Act
        sut.pingPis()

        // Assert
        Mockito.verify(piService).pingPi(1)
        Mockito.verify(piService).getAllPis()
        Mockito.verify(piService).setPiStatus(PiStatus.OFFLINE, 1)
    }
}