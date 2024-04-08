package nl.han.oose.colossus.backend.bakery2

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WelcomePage {
    @GetMapping(value = ["/test"], produces = [MediaType.TEXT_PLAIN_VALUE])
    fun helloWorld(): String {
        return "Hello World";
    }
}