package nl.han.oose.colossus.backend.bakery2

import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationServiceImp
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun authenticationService(): AuthenticationService{
        return AuthenticationServiceImp()
    }

}