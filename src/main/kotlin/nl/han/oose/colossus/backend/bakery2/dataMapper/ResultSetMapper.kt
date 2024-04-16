package nl.han.oose.colossus.backend.bakery2.dataMapper

import java.sql.ResultSet

interface ResultSetMapper<T> {
    fun map(resultSet: ResultSet): T
}
