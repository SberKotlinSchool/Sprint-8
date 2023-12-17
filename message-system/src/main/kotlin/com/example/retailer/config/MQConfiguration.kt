package com.example.retailer.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableRabbit
@Configuration
class MQConfiguration(
    private val queueName: String = "retailer",
    private val rabbitExchangeName: String = "distributor_exchange",
    private val consumerRoutingPrefix: String = "retailer.test"
) {
    @Bean
    fun jsonMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = jsonMessageConverter()
        return template
    }

    @Bean
    fun exchange(): TopicExchange = TopicExchange(rabbitExchangeName)

    @Bean
    fun queue(): Queue = Queue(queueName, false)

    @Bean
    fun binding(): Binding = BindingBuilder.bind(queue())
        .to(exchange())
        .with("$consumerRoutingPrefix.#")
}