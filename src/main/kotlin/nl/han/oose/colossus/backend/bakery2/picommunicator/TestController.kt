package nl.han.oose.colossus.backend.bakery2.picommunicator

import nl.han.oose.colossus.backend.bakery2.picommunicator.dto.SocketResponseDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Controller
@RestController
class WebSocketController {


    @Autowired
    private lateinit var messagingTemplate: SimpMessagingTemplate


    fun greeting(msg: String) {
        println("called msg: $msg")
        val test = SocketResponseDto()
        test.setInstruction("sign up")
        test.setBody(listOf("sign up succeeded"))
        messagingTemplate.convertAndSend("/topic/greetings", test)
    }

    @GetMapping("/py-test")
    fun greetings() {
        greeting("test greeting")
    }

}