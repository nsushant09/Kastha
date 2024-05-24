package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.repo.ModelRepo
import com.neupanesushant.kasthabackend.core.services.FileService
import com.neupanesushant.kasthabackend.data.model.BaseResponse
import com.neupanesushant.kasthabackend.data.model.ObjectModel
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
    @Autowired private val fileService: FileService,
    @Autowired private val modelRepo: ModelRepo
) {
    @Value("\${user.home}/kastha_data/glb")
    private lateinit var uploadDirectory: String

    @PostMapping
    fun upload(
        request: HttpServletRequest,
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<BaseResponse<String>> {
        val fileName = fileService.upload(uploadDirectory, file, ".glb")
        return ResponseEntity.ok(BaseResponse(true, fileUrl(fileName)))
    }

    @GetMapping("/{fileName}")
    fun get(@PathVariable("fileName") fileName: String): ResponseEntity<FileSystemResource> {
        return fileService.get(uploadDirectory, MediaType.parseMediaType("model/gltf-binary"), fileName)
    }

    private fun fileUrl(fileName: String) = "model/$fileName"

}