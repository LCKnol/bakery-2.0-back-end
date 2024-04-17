package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.Pi.PiService
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationDao
import nl.han.oose.colossus.backend.bakery2.authentication.AuthenticationService
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Primary
@Component
class UserServiceImp : UserService {
    @Autowired
    private lateinit var userDao : UserDao

    override fun getUserInfo(token: String): UserInfoDTO {
        val user = userDao.getUserInfo(token);
        return user    }

    override fun getUser(token: String): Int {
        val user = userDao.getUser(token);
        return user
    }
}





