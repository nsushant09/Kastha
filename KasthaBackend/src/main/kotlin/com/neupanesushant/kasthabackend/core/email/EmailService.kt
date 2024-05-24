package com.neupanesushant.kasthabackend.core.email

import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
abstract class EmailService(
    @Autowired private val mailSender: JavaMailSender,
    @Autowired private val env: Environment
) {
    protected var authenticationKey = "-1"
    fun send(to: String): HashMap<String, Any> {
        val response = HashMap<String, Any>()
        try {
            val mail = env.getProperty("spring.mail.username") ?: throw Exception("Mail not found")
            val message: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)
            helper.setFrom(InternetAddress(mail, "Kastha"))
            helper.setTo(to)
            helper.setText(emailContent(), true)
            helper.setSubject("One Time Password : $authenticationKey")

            mailSender.send(message)

            response["success"] = true
            response["authenticationKey"] = authenticationKey
        } catch (e: Exception) {
            response["success"] = false
            response["authenticationKey"] = "false"
        }

        return response
    }

    abstract fun emailContent(): String

    protected fun generateAuthenticationKey() {
        val random = Random()
        val randomNumber = random.nextInt(999999)
        authenticationKey = String.format("%06d", randomNumber)
    }
}