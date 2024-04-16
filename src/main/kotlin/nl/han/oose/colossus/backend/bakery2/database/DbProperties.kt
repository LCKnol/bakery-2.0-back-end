import java.io.IOException
import java.util.*

class DbProperties(propertiesFileName: String) {
    protected var properties: Properties = Properties()

    val connectionString: String
        get() = properties.getProperty(CONNECTIONSTRING) ?: throw RuntimeException("Connection string is null")

    val driver: String
        get() = properties.getProperty(DRIVER) ?: throw RuntimeException("Driver is null")

    val user: String
        get() = properties.getProperty(USER) ?: throw RuntimeException("User is null")

    val password: String
        get() = properties.getProperty(PASSWORD) ?: throw RuntimeException("Password is null")

    init {
        println("Loading '$propertiesFileName'")
        try {
            val inputStream = javaClass.classLoader.getResourceAsStream(propertiesFileName)
            if (inputStream != null) {
                properties.load(inputStream)
            } else {
                throw RuntimeException("Cannot load properties file: $propertiesFileName")
            }
        } catch (e: IOException) {
            throw RuntimeException("Error loading properties file: $propertiesFileName", e)
        }

        // Load the database driver class
        try {
            Class.forName(driver)
        } catch (e: ClassNotFoundException) {
            throw RuntimeException("Database driver class not found: $driver", e)
        }
    }

    companion object {
        private const val CONNECTIONSTRING = "connectionstring"
        private const val DRIVER = "driver"
        private const val USER = "user"
        private const val PASSWORD = "password"
    }
}
