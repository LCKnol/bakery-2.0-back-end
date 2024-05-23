package nl.han.oose.colossus.backend.bakery2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@SpringBootApplication
@EnableScheduling
class Application
    fun main(args: Array<String>) {
        runApplication<Application>(*args)
}
