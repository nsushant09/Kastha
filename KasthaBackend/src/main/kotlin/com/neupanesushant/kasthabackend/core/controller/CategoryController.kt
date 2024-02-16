package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.appcore.NetworkResponse
import com.neupanesushant.kasthabackend.core.services.CategoryService
import com.neupanesushant.kasthabackend.data.model.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("api/category")
class CategoryController(
    @Autowired private val categoryServices: CategoryService
) {
    @PostMapping("/")
    fun insert(@RequestBody category: Category) = ResponseEntity.ok(categoryServices.insert(category))

    @PutMapping("/")
    fun update(@RequestBody category: Category) = ResponseEntity.ok(categoryServices.update(category))

    @GetMapping("/all")
    fun all() = ResponseEntity.ok(categoryServices.all)

    @GetMapping("/of_id")
    fun ofId(@RequestParam("category_id") id: Int): ResponseEntity<Category> {
        val category = categoryServices.ofId(id)
        if (category.isEmpty)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        return ResponseEntity.ok(
            category.get()
        )
    }
}