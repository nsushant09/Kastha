package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.Alignment
import org.springframework.data.jpa.repository.JpaRepository

interface AlignmentRepo : JpaRepository<Alignment, Int> {}