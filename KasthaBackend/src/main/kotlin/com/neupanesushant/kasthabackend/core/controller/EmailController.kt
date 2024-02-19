package com.neupanesushant.kasthabackend.core.controller

import com.neupanesushant.kasthabackend.core.email.OTPEmailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/mail")
class EmailController(
    @Autowired @Qualifier("OTPEmailService") private val mailService: OTPEmailService
) {
    @RequestMapping(value = ["/otp"], method = [RequestMethod.GET])
    fun sendOTPEmail(@RequestParam("email") toEmail: String): ResponseEntity<HashMap<String, String>> {
        val mailResponse = mailService.send(toEmail)
        return ResponseEntity(mailResponse, HttpStatus.OK)
    }
}