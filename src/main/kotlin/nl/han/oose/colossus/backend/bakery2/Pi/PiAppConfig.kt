package nl.han.oose.colossus.backend.bakery2.Pi

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PiAppConfig {
    @Bean
    fun piService(piDao : PiDao): PiService {
        return PiServiceImp(piDao)
    }

    @Bean
    fun piDao(): PiDao {
        return PiDaoImp()
    }
}