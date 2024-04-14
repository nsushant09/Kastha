package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.services.FileService
import com.neupanesushant.kasthabackend.data.model.BaseResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/image")
class ImageController(
    @Autowired private val fileService: FileService
) {
    @Value("\${user.home}/kastha_data/image")
    private lateinit var uploadDirectory: String

    @PostMapping
    fun upload(
        request: HttpServletRequest,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<BaseResponse<String>> {
        val fileName = fileService.upload(uploadDirectory, file)
        return ResponseEntity.ok(BaseResponse(true, fileUrl(fileName)))
    }

    @GetMapping
    fun get(@RequestParam("fileName") fileName: String): ResponseEntity<FileSystemResource> {
        return fileService.get(uploadDirectory, fileName)
    }

    private fun fileUrl(fileName: String) = "/api/image/$fileName"

}