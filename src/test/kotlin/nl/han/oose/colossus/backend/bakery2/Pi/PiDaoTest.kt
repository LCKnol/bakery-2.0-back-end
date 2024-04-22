package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDao
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsDaoImp
import nl.han.oose.colossus.backend.bakery2.dashboards.DashboardsMapper
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import nl.han.oose.colossus.backend.bakery2.util.MockitoHelper
import nl.han.oose.colossus.backend.bakery2.util.ScriptRunner
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.io.InputStreamReader
import java.sql.ResultSet

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
    fun TestGetAllDashboardsCallWorksCorrectly() {

        // arrange
        val pi: PiCollectionDto = PiCollectionDto()
        Mockito.`when`(piMapper.mapPis(MockitoHelper.anyObject())).thenReturn(pi)

        // act
        val result = sut.getPis(1)

        //assert
        Mockito.verify(piMapper).mapPis(MockitoHelper.anyObject())
        Assertions.assertEquals(pi, result)
    }
}