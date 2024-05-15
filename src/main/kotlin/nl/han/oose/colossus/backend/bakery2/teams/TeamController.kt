package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import nl.han.oose.colossus.backend.bakery2.users.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/teams")
class TeamController {
    @Autowired
    private lateinit var teamService: TeamService

    @Autowired
    private lateinit var headerService: HeaderService

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getTeamsFromUser(): ResponseEntity<TeamCollectionDto> {
        val userId = headerService.getUserId()

        return ResponseEntity(teamService.getTeamsFromUser(userId), HttpStatus.OK)
    }
}