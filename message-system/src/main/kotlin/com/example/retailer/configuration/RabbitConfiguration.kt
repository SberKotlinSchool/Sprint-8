package com.example.retailer.configuration

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@EnableRabbit
@Configuration
class RabbitConfiguration {

  @Value("\${exchange.name}")
  private val exchangeName: String? = null

  @Value("\${retailer.routing.key}")
  private val routingKey: String? = null

  @Value("\${retailer.queue}")
  private val queueName: String? = null

  @Bean
  fun connectionFactory(): CachingConnectionFactory {
    return CachingConnectionFactory("localhost")
  }

  @Bean
  fun queue(): Queue {
    return Queue(queueName)
  }

  @Bean
  fun exchange(): TopicExchange {
    return TopicExchange(exchangeName)
  }

  @Bean
  fun messageConverter(): MessageConverter {
    return Jackson2JsonMessageConverter()
  }

  @Bean
  fun publisherBinding(queue: Queue?, exchange: TopicExchange?): Binding {
    return BindingBuilder
      .bind(queue)
      .to(exchange)
      .with(routingKey)
  }

  @Bean
  fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
    val rabbitTemplate = RabbitTemplate(connectionFactory)
    rabbitTemplate.messageConverter = messageConverter()
    return rabbitTemplate
  }

}