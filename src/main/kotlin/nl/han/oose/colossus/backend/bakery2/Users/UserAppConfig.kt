package nl.han.oose.colossus.backend.bakery2.Users

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UserAppConfig {
    @Bean
    fun userService(userDao : UserDao): UserService {
        return UserServiceImp(userDao)
    }

    @Bean
    fun userDao(): UserDao {
        return UserDaoImp()
    }
}