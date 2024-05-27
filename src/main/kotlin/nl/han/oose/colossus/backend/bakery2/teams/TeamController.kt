package nl.han.oose.colossus.backend.bakery2.teams

import nl.han.oose.colossus.backend.bakery2.dto.TeamCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.TeamInfoDto
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/teams")
class TeamController {
    @Autowired
    private lateinit var teamService: TeamService

    @Autowired
    private lateinit var headerService: HeaderService

    fun setTeamService(teamService: TeamService) {
        this.teamService = teamService
    }

    fun setHeaderService(headerService: HeaderService) {
        this.headerService = headerService
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getTeamsFromCurrentUser(): ResponseEntity<TeamCollectionDto> {
        val userId = headerService.getUserId()

        return ResponseEntity(teamService.getTeamsFromUser(userId), HttpStatus.OK)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun addTeam(@RequestBody teamInfoDto: TeamInfoDto): ResponseEntity<HttpStatus> {
        teamService.addTeam(teamInfoDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @GetMapping(path = ["/notinroom/{roomNo}"], produces = ["application/json"])
    @Authenticate
    fun getTeamsNotInRoom(@PathVariable roomNo: String): ResponseEntity<TeamCollectionDto> {
        return ResponseEntity(teamService.getTeamsNotInRoom(roomNo.trim()), HttpStatus.OK)
    }

    @GetMapping(path = ["/user/{userId}"],produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getTeamsFromUser(@PathVariable userId: Int): ResponseEntity<TeamCollectionDto> {
        return ResponseEntity(teamService.getTeamsFromUser(userId), HttpStatus.OK)
    }

    @GetMapping(path = ["/all"],produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun getAllTeams(): ResponseEntity<TeamCollectionDto> {
        return ResponseEntity(teamService.getAllTeams(), HttpStatus.OK)
    }

    @PostMapping(path = ["assignToTeam/{userId}/{teamId}"],produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun assignUsertoTeam(@PathVariable userId: Int,@PathVariable teamId: Int): ResponseEntity<HttpStatus> {
        teamService.assignUserToTeam(userId,teamId)

        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping(path = ["removeFromTeam/{userId}/{teamId}"],produces = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun removeUserFromTeam(@PathVariable userId: Int,@PathVariable teamId: Int): ResponseEntity<HttpStatus> {
        teamService.removeUserFromTeam(userId,teamId)
        return ResponseEntity(HttpStatus.OK)
    }
}