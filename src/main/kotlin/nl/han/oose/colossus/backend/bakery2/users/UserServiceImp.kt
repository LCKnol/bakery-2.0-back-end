package nl.han.oose.colossus.backend.bakery2.users

import nl.han.oose.colossus.backend.bakery2.dto.UserDto
import nl.han.oose.colossus.backend.bakery2.dto.UserInfoDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Component

@Primary
@Component
class UserServiceImp : UserService {
    @Autowired
    private lateinit var userDao: UserDao

    override fun setUserDao(dao: UserDao) {
        userDao = dao
    }

    override fun getUserInfo(token: String): UserInfoDto {
        val user = userDao.getUserInfo(token)
        return user
    }



    override fun getUserId(token: String): Int {
        val user: UserDto? = userDao.getUser(token)
        if (user != null) {
            val id = user.getId()
            return id
        }
        // TODO: decide how to handle this: throw some exception???
        return -1
    }

    override fun registerUser(userDto: UserDto) {
        userDto.setPassword(BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt()))
        userDao.insertUser(userDto)
    }
}





