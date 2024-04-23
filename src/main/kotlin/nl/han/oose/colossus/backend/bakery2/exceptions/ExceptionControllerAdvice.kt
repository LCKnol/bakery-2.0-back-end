package nl.han.oose.colossus.backend.bakery2.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(HttpUnauthorizedException::class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    fun handle(e: HttpUnauthorizedException): ResponseEntity<String> {
        val responseBody = "Unauthorized: ${e.message}"
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody)
    }

    @ExceptionHandler(HttpForbiddenException::class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    fun handle(e: HttpForbiddenException): ResponseEntity<String> {
        val responseBody = "Forbidden: ${e.message}"
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody)
    }

    @ExceptionHandler(HttpNotFoundException::class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handle(e: HttpNotFoundException): ResponseEntity<String> {
        val responseBody = "Not found: ${e.message}"
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody)
    }
}