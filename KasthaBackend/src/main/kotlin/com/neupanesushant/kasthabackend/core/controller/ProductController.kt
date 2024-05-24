package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.repo.UserRepo
import com.neupanesushant.kasthabackend.core.services.CategoryService
import com.neupanesushant.kasthabackend.core.services.ProductService
import com.neupanesushant.kasthabackend.data.model.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/product")
class ProductController(
    @Autowired private val productService: ProductService,
    @Autowired private val categoryService: CategoryService
) {

    @PostMapping
    fun insert(
        @RequestBody product: Product
    ): ResponseEntity<Product> {
        return ResponseEntity.ok(productService.insert(product))
    }

    @PutMapping("/{isModelChanged}/{areImagesChanged}")
    fun update(
        @RequestBody product: Product,
        @PathVariable("isModelChanged") isModelChanged: Boolean,
        @PathVariable("areImagesChanged") areImagesChanged: Boolean
    ): ResponseEntity<Product> {
        return ResponseEntity.ok(productService.update(product, isModelChanged, areImagesChanged))
    }

    @GetMapping("/id/{product_id}")
    fun byId(@PathVariable("product_id") productId: Int): ResponseEntity<Product> {
        return ResponseEntity.ok(productService.byId(productId))
    }

    @GetMapping("/category/{category_id}")
    fun byCategory(@PathVariable("category_id") categoryId: Int): ResponseEntity<Collection<Product>> {
        val category = categoryService.ofId(categoryId).getOrNull() ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(productService.byCategory(category))
    }

    @GetMapping("/search")
    fun bySearchValue(@RequestParam("value") value: String): ResponseEntity<Collection<Product>> {
        return ResponseEntity.ok(productService.bySearch(value))
    }

    @GetMapping("/latest")
    fun latest(): ResponseEntity<Collection<Product>> = ResponseEntity.ok(productService.latest())

    @GetMapping("/recommended")
    fun recommended(
        @RequestParam("category_ids") categoryIds: List<Int>
    ): ResponseEntity<Collection<Product>> {
        val categories = categoryIds.map {
            categoryService.ofId(it).getOrNull()
        }.filterNotNull()
        return ResponseEntity.ok(productService.recommended(categories))
    }

    @GetMapping
    fun all(): Collection<Product> {
        return productService.all
    }
}