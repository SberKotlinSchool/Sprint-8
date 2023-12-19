package com.example.retailer.endpoint

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class RetailEndpoint {

  @Autowired
  lateinit var orderService: OrderService

  @PostMapping("/placeOrder")
  fun placeOrder(@RequestBody orderDraft: Order): OrderInfo = orderService.placeOrder(orderDraft)

  @GetMapping("/view/{id}")
  fun viewOrder(@PathVariable("id") id: String): OrderInfo? = orderService.getOrderInfo(id)

}

