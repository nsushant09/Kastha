package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.Role
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.*

interface RoleRepo : CoroutineCrudRepository<Role, Int> {
    fun findByName(name : String) : Role?
}