package com.neupanesushant.kasthabackend.core.services

import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.util.*

@Service
class FileService {
    fun upload(uploadDirectory: String, file: MultipartFile, extension: String = ""): String {
        if (file.isEmpty) {
            return "Please select a file to upload."
        }
        val uuid = UUID.randomUUID()
        val fileName = StringUtils.cleanPath(file.originalFilename ?: uuid.toString()) + extension
        val filePath = "$uploadDirectory/$fileName"
        FileOutputStream(filePath).use { outputStream ->
            outputStream.write(file.bytes)
        }
        return fileName
    }

    fun get(uploadDirectory: String, mediaType: MediaType, fileName: String): ResponseEntity<FileSystemResource> {
        val filePath = "$uploadDirectory/$fileName"
        val imageFile = File(filePath)
        if (!imageFile.exists()) {
            return ResponseEntity.notFound().build<FileSystemResource>()
        }
        return ResponseEntity
            .ok()
            .contentType(mediaType)
            .body(FileSystemResource(imageFile))
    }
}