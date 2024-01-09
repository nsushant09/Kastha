package com.neupanesushant.kasthabackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@SpringBootApplication
class KasthaBackendApplication

fun main(args: Array<String>) {
	runApplication<KasthaBackendApplication>(*args)
}
