import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import java.io.IOException

@RestController
class RebootController {

    @PostMapping("/api/reboot")
    fun rebootRaspberryPi(): ResponseEntity<String> {
        try {
            val processBuilder = ProcessBuilder("sudo", "reboot")
            processBuilder.redirectErrorStream(true)  // Redirect error stream to the standard output

            val process = processBuilder.start()
            process.waitFor()

            process.inputStream.bufferedReader().use {
                it.lines().forEach { line -> println(line) }  // Print output and error stream
            }

            return ResponseEntity.ok("Reboot command executed.")
        } catch (e: IOException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to execute reboot command: ${e.message}")
        } catch (e: InterruptedException) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Reboot command interrupted: ${e.message}")
        }
    }
}
