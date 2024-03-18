package io.github.dsvdev.letovo_os.entrypoint.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController {
    @GetMapping
    fun index(): ResponseEntity<String> {
        return ResponseEntity.ok("Test")
    }
}