package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.token.Authenticate
import nl.han.oose.colossus.backend.bakery2.token.HeaderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboards")

class DashboardsController {

    @Autowired
    private lateinit var dashboardsService: DashboardsService

    @Autowired
    private lateinit var headerService: HeaderService

    @Autowired
    private lateinit var userService: UserService

    fun setDashboardsService(service: DashboardsService) {
        dashboardsService = service
    }

    fun setTokenService(service: HeaderService) {
        headerService = service
    }

    fun setUserService(service: UserService) {
        userService = service
    }

    @GetMapping(produces = ["application/json"])
    @Authenticate
    fun getAllDashboards(): ResponseEntity<DashboardCollectionDto> {

        val result: DashboardCollectionDto = this.dashboardsService.getAllDashboards()
        return ResponseEntity<DashboardCollectionDto>(result, HttpStatus.OK)
    }

    @GetMapping(path = ["/{dashboardId}"], produces = ["application/json"])
    fun getDashboard(@PathVariable("dashboardId") dashboardId: Int): ResponseEntity<DashboardDto> {
        val result = this.dashboardsService.getDashboard(dashboardId)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun addDashboards(@RequestBody dashboardDto: DashboardDto): ResponseEntity<HttpStatus> {
        val token = headerService.getToken()
        var userId = userService.getUserId(token)
        dashboardDto.setUserId(userId)
        this.dashboardsService.addDashboard(dashboardDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun editDashboard(@RequestBody dashboardDto: DashboardDto): ResponseEntity<HttpStatus> {
        val token = this.headerService.getToken()
        val userId = this.userService.getUserId(token)
        this.dashboardsService.editDashboard(dashboardDto, userId)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping(path = ["/{dashboardId}"])
    @Authenticate
    fun deleteDashboard(@PathVariable dashboardId: Int) {
        val token = headerService.getToken()
        val userId = userService.getUserId(token)
        dashboardsService.deleteDashboard(dashboardId, userId)
    }
}