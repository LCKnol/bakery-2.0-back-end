import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionSubscribeEvent

@Component
class WebSocketEventListener {

    @EventListener
    fun handleSubscriptionEvent(event: SessionSubscribeEvent) {
        val headerAccessor = StompHeaderAccessor.wrap(event.message)
        val sessionId = headerAccessor.sessionId
        val destination = headerAccessor.destination

        // Log the subscription details
        println("New subscription: Session ID $sessionId subscribed to $destination")
    }
}
