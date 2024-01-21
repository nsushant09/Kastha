package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface RoleRepo : JpaRepository<Role, Int> {
    fun findByName(name : String) : Role?
}