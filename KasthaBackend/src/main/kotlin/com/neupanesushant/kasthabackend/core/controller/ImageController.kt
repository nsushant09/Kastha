package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.repo.ImageRepo
import com.neupanesushant.kasthabackend.core.services.FileService
import com.neupanesushant.kasthabackend.data.model.BaseResponse
import com.neupanesushant.kasthabackend.data.model.Image
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/image")
class ImageController(
    @Autowired private val fileService: FileService,
    @Autowired private val imageRepo: ImageRepo
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

    @PostMapping("/multiple")
    fun upload(
        request: HttpServletRequest,
        @RequestParam("files") files: List<MultipartFile>
    ): ResponseEntity<BaseResponse<List<String>>> {
        val uploadedFileNames = mutableListOf<String>()
        for (file in files) {
            val fileName = fileService.upload(uploadDirectory, file, ".jpg")
            uploadedFileNames.add(fileUrl(fileName))
        }
        return ResponseEntity.ok(BaseResponse(true, uploadedFileNames))
    }

    @GetMapping("/{fileName}")
    fun get(@PathVariable("fileName") fileName: String): ResponseEntity<FileSystemResource> {
        return fileService.get(uploadDirectory, MediaType.IMAGE_JPEG, fileName)
    }

    private fun fileUrl(fileName: String) = "image/$fileName"

}