package nl.han.oose.colossus.backend.bakery2.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WelcomePage {
    @GetMapping(value = ["/hello"], produces = [MediaType.TEXT_PLAIN_VALUE])
    fun helloWorld(): String {
        return "Hello World";
    }
}
