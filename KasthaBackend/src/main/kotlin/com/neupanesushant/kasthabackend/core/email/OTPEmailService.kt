package com.neupanesushant.kasthabackend.core.email

import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.env.Environment
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.HashMap

@Service
@Qualifier("OTPEmailService")
class OTPEmailService(
    @Autowired private val mailSender: JavaMailSender,
    @Autowired private val env: Environment
) : EmailService {

    private var authenticationKey = "-1"
    override fun send(to: String): HashMap<String, Any> {
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

    override fun emailContent(): String {
        generateAuthenticationKey()
        return "<h2>Your One-Time Password</h2>" +
                "<p>Use the following one-time password to log in to your account:</p>" +
                "<p><strong>" + authenticationKey + "</strong></p>" +
                "<p>This password is valid for one use only and will expire in 5 minutes.</p>" +
                "<p>If you did not request this password, please contact us immediately.</p>" +
                "<p>Thank you,<br> Kastha</p>"
    }

    fun generateAuthenticationKey() {
        val random = Random()
        val randomNumber = random.nextInt(999999)
        authenticationKey = String.format("%06d", randomNumber)
    }
}