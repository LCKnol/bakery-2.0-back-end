package nl.han.oose.colossus.backend.bakery2.dashboards

import nl.han.oose.colossus.backend.bakery2.dto.DashboardCollectionDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dashboards")

class DashboardsController {

    @Autowired
    private lateinit var dashboardsService : DashboardsService

    fun setDashboardsService(service: DashboardsService) {
        dashboardsService = service
    }

    @GetMapping(produces = ["application/json"])

    fun getAllDashboards(@RequestParam token : String): ResponseEntity<DashboardCollectionDto>{

        val result: DashboardCollectionDto = this.dashboardsService.getAllDashboards()
        return ResponseEntity<DashboardCollectionDto>(result,HttpStatus.OK)
    }

}