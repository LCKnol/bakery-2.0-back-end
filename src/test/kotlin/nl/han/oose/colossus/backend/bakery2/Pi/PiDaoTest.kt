package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiRequestsCollectionDto
import nl.han.oose.colossus.backend.bakery2.pi.PiDao
import nl.han.oose.colossus.backend.bakery2.pi.PiDaoImp
import nl.han.oose.colossus.backend.bakery2.pi.PiMapper
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.InputStreamReader


class PiDaoTest {

    private lateinit var  sut: PiDao

    private lateinit var piMapper: PiMapper

    @Test
    @BeforeEach
    fun setup() {
        sut = PiDaoImp()
        piMapper = mock(PiMapper::class.java)
        val dbconnection: DatabaseConnection = DatabaseConnection()
        val scriptRunner: ScriptRunner = ScriptRunner(dbconnection.getConnection(), true, true)
        scriptRunner.runScript(InputStreamReader(ClassLoader.getSystemResourceAsStream("BakeryDB_Create.sql")!!))
        sut.setDatabaseConnection(dbconnection)
        sut.setPiMapper(piMapper)
    }

    @Test
    fun TestGetPisCallWorksCorrectly() {

        // arrange
        val pi: PiCollectionDto = PiCollectionDto()
        `when`(piMapper.mapPis(MockitoHelper.anyObject())).thenReturn(pi)

        // act
        val result = sut.getPis(1)

        //assert
        verify(piMapper).mapPis(MockitoHelper.anyObject())
        Assertions.assertEquals(pi, result)
    }

    @Test
    fun testgetAllPisCallWorksCorrectly() {
        // arrange
        val pi: PiCollectionDto = PiCollectionDto()
        `when`(piMapper.mapPis(MockitoHelper.anyObject())).thenReturn(pi)

        // act
        val result = sut.getAllPis()

        //assert
        verify(piMapper).mapPis(MockitoHelper.anyObject())
        Assertions.assertEquals(pi, result)
    }

    @Test
    fun testgetAllPirequestsCallWorksCorrectly() {
        // arrange
        val piRequests: PiRequestsCollectionDto = PiRequestsCollectionDto()
        `when`(piMapper.mapPiRequests(MockitoHelper.anyObject())).thenReturn(piRequests)

        // act
        val result = sut.getAllPiRequests()

        //assert
        verify(piMapper).mapPiRequests(MockitoHelper.anyObject())
        Assertions.assertEquals(piRequests, result)
    }
}