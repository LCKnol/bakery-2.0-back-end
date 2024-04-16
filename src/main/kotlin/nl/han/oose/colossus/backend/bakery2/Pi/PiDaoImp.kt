package nl.han.oose.colossus.backend.bakery2.Pi

import nl.han.oose.colossus.backend.bakery2.Users.UserMapperImp
import nl.han.oose.colossus.backend.bakery2.database.DatabaseConnection
import nl.han.oose.colossus.backend.bakery2.dto.PiCollectionDTO
import nl.han.oose.colossus.backend.bakery2.dto.PiDTO
import org.springframework.context.annotation.Primary

@Primary
class PiDaoImp : PiDao {
    val dbConnection = DatabaseConnection()
    val piMapper = PiMapperImp()


    override fun getPis(user: Int): PiCollectionDTO {
        val preparedStatement = dbConnection.prepareStatement("SELECT p.*, d.NAME AS dashboardname FROM Pi p INNER JOIN DASHBOARD d ON p.DASHBOARDID = d.DASHBOARDID WHERE p.roomno IN (SELECT roomno FROM teaminroom WHERE teamid IN (SELECT teamid FROM userinteam WHERE code = ?))")
        preparedStatement.setInt(1, user)
        val resultSet = preparedStatement.executeQuery()
        val pis = piMapper.mapPis(resultSet)
        resultSet.close()
        preparedStatement.close()
        return pis

//        var pi1 = PiDTO()
//        var pi2 = PiDTO()
//        pi1.setDisplay("cool dashboard")
//        pi1.setName("12-12-12")
//        pi1.setStatus("online")
//        pi2.setDisplay("lame dashboard")
//        pi2.setName("12-12-11")
//        pi2.setStatus("offline")
//        var pis = PiCollectionDTO()
//        pis.setPis(arrayListOf( pi1, pi2))
//        return pis
    }
}