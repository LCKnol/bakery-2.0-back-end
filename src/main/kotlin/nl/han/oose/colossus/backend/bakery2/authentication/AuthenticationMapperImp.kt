package nl.han.oose.colossus.backend.bakery2.authentication

import nl.han.oose.colossus.backend.bakery2.dataMapper.ResultSetMapper
import java.sql.ResultSet

class AuthenticationMapperImp : ResultSetMapper<String> {
    override fun map(resultSet: ResultSet): String {
        return if (resultSet.next()) {
            resultSet.getString("password")
        } else {
            "Password not found"
        }
    }
}
