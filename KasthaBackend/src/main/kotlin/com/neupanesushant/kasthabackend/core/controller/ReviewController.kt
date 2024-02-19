package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.services.ReviewService
import com.neupanesushant.kasthabackend.data.dtomodel.ReviewDTO
import com.neupanesushant.kasthabackend.data.mapper.Mapper
import com.neupanesushant.kasthabackend.data.model.Review
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.history.support.RevisionEntityInformation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
    @PostMapping
    fun insert(
        @RequestParam("product_id") productId: Int,
        @RequestParam("user_id") userId: Int,
        @RequestBody review: Review
    ): ResponseEntity<Review> {
        return ResponseEntity.ok(reviewService.insert(review, userId, productId))
    }

    @GetMapping
    fun all(@RequestParam("product_id") productId: Int): ResponseEntity<List<ReviewDTO>> {
        return ResponseEntity.ok(reviewService.byId(productId).map(Mapper::toDto))
    }
}