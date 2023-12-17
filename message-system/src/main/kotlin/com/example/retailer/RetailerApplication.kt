package com.example.retailer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@SpringBootApplication
class RetailerApplication

fun main(args: Array<String>) {
	runApplication<RetailerApplication>(*args)
}
