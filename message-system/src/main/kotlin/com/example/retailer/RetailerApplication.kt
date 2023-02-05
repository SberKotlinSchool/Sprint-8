package com.example.retailer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
class RetailerApplication

fun main(args: Array<String>) {
	runApplication<RetailerApplication>(*args)
}
