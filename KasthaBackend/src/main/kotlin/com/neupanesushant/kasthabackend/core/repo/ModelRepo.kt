package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.ObjectModel
import jakarta.persistence.criteria.CriteriaBuilder.In
import org.springframework.data.jpa.repository.JpaRepository

interface ModelRepo : JpaRepository<ObjectModel, Int> {

}