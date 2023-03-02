package com.example.docker

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DockerApplication

fun main(args: Array<String>) {
	runApplication<DockerApplication>(*args)
}
