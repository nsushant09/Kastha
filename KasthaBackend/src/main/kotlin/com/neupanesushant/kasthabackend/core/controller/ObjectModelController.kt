package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.services.FileService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*

@RestController
@RequestMapping("/api/model")
class ObjectModelController(
    @Autowired private val fileService: FileService
) {
    @Value("\${user.home}/kastha_data/glb")
    private lateinit var uploadDirectory: String

    @PostMapping
    fun upload(request: HttpServletRequest, @RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        val fileName = fileService.upload(uploadDirectory, file)
        return ResponseEntity.ok(fileUrl(fileName))
    }

    @GetMapping
    fun get(@RequestParam("fileName") fileName: String): ResponseEntity<FileSystemResource> {
        return fileService.get(uploadDirectory, fileName)
    }

    private fun fileUrl(fileName: String) = "/api/model/$fileName"

}