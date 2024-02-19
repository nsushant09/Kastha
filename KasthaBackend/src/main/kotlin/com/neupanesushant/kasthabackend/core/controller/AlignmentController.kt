package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.appcore.NetworkResponse
import com.neupanesushant.kasthabackend.core.services.AlignmentService
import com.neupanesushant.kasthabackend.data.model.Alignment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/alignment")
class AlignmentController(
    @Autowired private val alignmentService: AlignmentService
) {
    @PostMapping("/")
    fun insert(@RequestBody alignment: Alignment) = ResponseEntity.ok(alignmentService.insert(alignment))

    @PutMapping("/")
    fun update(@RequestBody alignment: Alignment) = ResponseEntity.ok(alignmentService.update(alignment))

    @GetMapping("/all")
    fun all() = ResponseEntity.ok(alignmentService.all)

    @GetMapping("/of_id")
    fun ofId(@RequestParam("alignment_id") id: Int): ResponseEntity<Alignment> {
        val alignment = alignmentService.ofId(id)
        if (alignment.isEmpty)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build<Alignment>()
        return ResponseEntity.ok(alignment.get())
    }
}