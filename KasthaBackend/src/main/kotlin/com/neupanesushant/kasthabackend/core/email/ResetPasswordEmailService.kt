package com.neupanesushant.kasthabackend.core.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.env.Environment
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import java.util.*

@Service
@Qualifier("ResetPasswordEmailService")
class ResetPasswordEmailService(
    @Autowired private val mailSender: JavaMailSender,
    @Autowired private val env: Environment
) : EmailService(mailSender, env) {
    override fun emailContent(): String {
        generateAuthenticationKey()
        return "<h2>Password Reset Request</h2>" +
                "<p>We received a request to reset your account password.</p>" +
                "<p>Please use the following one-time code to reset your password:</p>" +
                "<p><strong>" + authenticationKey + "</strong></p>" +
                "<p>This code is valid for one use only and will expire in 5 minutes.</p>" +
                "<p>If you did not initiate this request, please ignore this email.</p>" +
                "<p>For security reasons, do not share this code with anyone.</p>" +
                "<p>Thank you,<br> Kastha</p>"
    }
}