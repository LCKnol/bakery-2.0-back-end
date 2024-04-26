package nl.han.oose.colossus.backend.bakery2.picommunicator

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class WebSocketController {

    @MessageMapping("/greetings")
    @SendTo("/topic/greetings")
    fun greeting(msg: String): TestDto {
        println("called msg: $msg")
        val test = TestDto()
        test.setInstruction("sign up")
        test.setBody(listOf("sign up succeeded"))
        return test
    }

}