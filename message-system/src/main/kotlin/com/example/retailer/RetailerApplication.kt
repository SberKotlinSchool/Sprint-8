package com.example.retailer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories
class RetailerApplication

fun main(args: Array<String>) {
	runApplication<RetailerApplication>(*args)
}
