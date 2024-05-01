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
) : EmailService(mailSender, env) {
    override fun emailContent(): String {
        generateAuthenticationKey()
        return "<h2>Your One-Time Password</h2>" +
                "<p>Use the following one-time password to log in to your account:</p>" +
                "<p><strong>" + super.authenticationKey + "</strong></p>" +
                "<p>This password is valid for one use only and will expire in 5 minutes.</p>" +
                "<p>If you did not request this password, please contact us immediately.</p>" +
                "<p>Thank you,<br> Kastha</p>"
    }
}