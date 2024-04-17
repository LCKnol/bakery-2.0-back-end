package nl.han.oose.colossus.backend.bakery2.Users

import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDTO
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Component
@Primary
class UserServiceImp: UserService{
    private val userDao = UserDaoImp()


    override fun getUserInfo(token: String): UserInfoDTO{
        val user = userDao.getUserInfo(token);
        return user
    }

    override fun getUser(token: String): Int{
        val user = userDao.getUser(token);
        return user
    }
}