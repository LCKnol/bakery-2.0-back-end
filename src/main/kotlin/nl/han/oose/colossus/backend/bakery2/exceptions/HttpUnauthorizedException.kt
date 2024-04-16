package nl.han.oose.colossus.backend.bakery2.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
class HttpUnauthorizedException(message: String): RuntimeException(message)