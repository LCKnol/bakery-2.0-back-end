package nl.han.oose.colossus.backend.bakery2.database

import java.io.IOException
import java.util.*


class DbProperties(propertiesFileName: String) {
    protected var properties: Properties = Properties()

    val connectionString: String
        get() = properties.getProperty(CONNECTIONSTRING)

    val driver: String
        get() = properties.getProperty(DRIVER)

    init {
        println("Loading '$propertiesFileName'")
        try {
            properties.load(javaClass.classLoader.getResourceAsStream(propertiesFileName))
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        // Laad de in de `.properties` ingesteld database driver/klasse met onderstaand stukje magisch `Class.forName`
        // code, zodat DAO-objecten de database connectie kunnen opvragen.
        try {
            Class.forName(properties.getProperty("driver"))
        } catch (e: ClassNotFoundException) {
            throw RuntimeException(e)
        }
    }

    val user: String
        get() = properties.getProperty(USER)

    val password: String
        get() = properties.getProperty(PASSWORD)

    companion object {
        private const val CONNECTIONSTRING = "connectionstring"
        private const val DRIVER = "driver"
        private const val USER = "user"
        private const val PASSWORD = "password"
    }
}