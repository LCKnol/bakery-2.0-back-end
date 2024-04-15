package nl.han.oose.colossus.backend.bakery2

import nl.han.oose.colossus.backend.bakery2.Pi.PiDao
import nl.han.oose.colossus.backend.bakery2.Pi.PiDaoImp
import nl.han.oose.colossus.backend.bakery2.Pi.PiService
import nl.han.oose.colossus.backend.bakery2.Pi.PiServiceImp
import nl.han.oose.colossus.backend.bakery2.Users.UserDao
import nl.han.oose.colossus.backend.bakery2.Users.UserDaoImp
import nl.han.oose.colossus.backend.bakery2.Users.UserService
import nl.han.oose.colossus.backend.bakery2.Users.UserServiceImp
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDao
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDaoImp
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationServiceImp
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AppConfig {
    @Bean
    fun authenticationService(authenticationDao : AuthenticationDao): AuthenticationService{
        return AuthenticationServiceImp(authenticationDao)
    }

    @Bean
    fun authenticationDao(): AuthenticationDao {
        return AuthenticationDaoImp()
    }

    @Bean
    fun userService(userDao : UserDao): UserService {
        return UserServiceImp(userDao)
    }

    @Bean
    fun userDao(): UserDao {
        return UserDaoImp()
    }

    @Bean
    fun piService(piDao : PiDao): PiService {
        return PiServiceImp(piDao)
    }

    @Bean
    fun piDao(): PiDao {
        return PiDaoImp()
    }


}