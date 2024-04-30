package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.services.ReviewService
import com.neupanesushant.kasthabackend.data.dtomodel.ReviewDTO
import com.neupanesushant.kasthabackend.data.mapper.Mapper
import com.neupanesushant.kasthabackend.data.model.Review
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.history.support.RevisionEntityInformation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/review")
class ReviewController(
    @Autowired private val reviewService: ReviewService
) {
    @PostMapping("/{product_id}/{user_id}")
    fun insert(
        @PathVariable("product_id") productId: Int,
        @PathVariable("user_id") userId: Int,
        @RequestBody review: Review
    ): ResponseEntity<ReviewDTO> {
        val newReview =
            reviewService.insert(review, userId, productId) ?: return ResponseEntity.notFound().build<ReviewDTO>()
        return ResponseEntity.ok(Mapper.toDto(newReview))
    }

    @GetMapping("/{product_id}")
    fun all(@PathVariable("product_id") productId: Int): ResponseEntity<List<ReviewDTO>> {
        return ResponseEntity.ok(reviewService.byId(productId).map(Mapper::toDto))
    }
}