package com.neupanesushant.kasthabackend.controller

import com.neupanesushant.kasthabackend.model.Response
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/home")
class HomeController {
    @GetMapping
    fun test() : ResponseEntity<Response<String>>{
        return ResponseEntity.ok(Response(data = "Home Data"))
    }
}