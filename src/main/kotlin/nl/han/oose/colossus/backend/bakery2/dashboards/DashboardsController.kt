package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import nl.han.oose.colossus.backend.bakery2.dto.DashboardDto
import nl.han.oose.colossus.backend.bakery2.token.Authenticate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dashboards")

class DashboardsController {

    @Autowired
    private lateinit var dashboardsService : DashboardsService

    fun setDashboardsService(service: DashboardsService) {
        dashboardsService = service
    }

    @GetMapping(produces = ["application/json"])
    @Authenticate
    fun getAllDashboards(): ResponseEntity<DashboardCollectionDto>{

        val result: DashboardCollectionDto = this.dashboardsService.getAllDashboards()
        return ResponseEntity<DashboardCollectionDto>(result,HttpStatus.OK)
    }

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun addDashboards(@RequestBody dashboardDto: DashboardDto): ResponseEntity<HttpStatus> {
        this.dashboardsService.addDashboard(dashboardDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PutMapping(path = ["/{dashboardId}"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Authenticate
    fun editDashboard(@PathVariable dashboardId: Int, @RequestBody dashboardDto: DashboardDto): ResponseEntity<HttpStatus> {
        this.dashboardsService.editDashboard(dashboardDto)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}