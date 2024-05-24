package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.Image
import jakarta.persistence.criteria.CriteriaBuilder.In
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepo : JpaRepository<Image, Int> {
}