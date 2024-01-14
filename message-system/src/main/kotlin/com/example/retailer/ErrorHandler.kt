package com.example.retailer

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class ErrorHandler {

    @ExceptionHandler
    fun handle(ex: ServerWebInputException): ResponseEntity<*> = ResponseEntity<Any>(HttpStatus.BAD_REQUEST)
}