package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.users.UserService
import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.header.Authenticate
import nl.han.oose.colossus.backend.bakery2.header.HeaderService
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
        val userId = this.headerService.getUserId()
        val result: DashboardCollectionDto = this.dashboardsService.getAllDashboards(userId)
        return ResponseEntity<DashboardCollectionDto>(result, HttpStatus.OK)
    }

    @GetMapping(path = ["/{dashboardId}"], produces = ["application/json"])
    @Authenticate
    fun getDashboard(@PathVariable("dashboardId") dashboardId: Int): ResponseEntity<DashboardDto> {
        val userId = this.headerService.getUserId()
        val result = this.dashboardsService.getDashboard(dashboardId, userId)
        return ResponseEntity(result, HttpStatus.OK)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun addDashboard(@RequestBody dashboardDto: DashboardDto): ResponseEntity<HttpStatus> {
        val userId = headerService.getUserId()

        userService.checkUserInTeam(userId, dashboardDto.getTeam().getId())
        this.dashboardsService.addDashboard(dashboardDto)
        return ResponseEntity(HttpStatus.CREATED)
    }


    @PutMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun editDashboard(@RequestBody dashboardDto: DashboardDto): ResponseEntity<HttpStatus> {
        val userId = this.headerService.getUserId()

        userService.checkUserInTeam(userId, dashboardDto.getTeam().getId())
        this.dashboardsService.editDashboard(dashboardDto)
        return ResponseEntity(HttpStatus.OK)
    }

    @DeleteMapping(path = ["/{dashboardId}"])
    @Authenticate
    fun deleteDashboard(@PathVariable dashboardId: Int): ResponseEntity<HttpStatus> {
        val userId = headerService.getUserId()

        val dashboard = dashboardsService.getDashboard(dashboardId, userId)
        userService.checkUserInTeam(userId, dashboard.getTeam().getId())
        dashboardsService.deleteDashboard(dashboardId)
        return ResponseEntity(HttpStatus.OK)
    }
}