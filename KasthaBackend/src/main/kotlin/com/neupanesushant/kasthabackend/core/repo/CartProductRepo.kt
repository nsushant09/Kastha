package com.neupanesushant.kasthabackend.core.repo

import com.neupanesushant.kasthabackend.data.model.CartProduct
import org.springframework.data.jpa.repository.JpaRepository

interface CartProductRepo : JpaRepository<CartProduct, Int> {
}