package com.neupanesushant.kasthabackend.core.controller

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/api/home")
class HomeController {
    @GetMapping
    fun test() : ResponseEntity<Any>{
        return ResponseEntity.ok("Home data")
    }
}