package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
class PiDaoImp : PiDao {
    @Autowired
    private lateinit var piMapper : PiMapper
    @Autowired
    private lateinit var dbConnection: DatabaseConnection



    override fun getPis(user: Int): PiCollectionDto {
        val preparedStatement = dbConnection.prepareStatement("SELECT p.*, d.NAME AS dashboardname FROM Pi p INNER JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID WHERE p.roomno IN (SELECT roomno FROM teaminroom WHERE teamid IN (SELECT teamid FROM userinteam WHERE userid = ?))")
        preparedStatement.setInt(1, user)
        val resultSet = preparedStatement.executeQuery()
        val pis = piMapper.mapPis(resultSet)
        resultSet.close()
        preparedStatement.close()
        return pis

    }
}