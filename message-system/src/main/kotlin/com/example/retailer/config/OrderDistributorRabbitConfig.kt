package com.example.retailer.config

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessagePostProcessor
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
class OrderDistributorRabbitConfig(
    private val objectMapper: ObjectMapper,
    private val messagePostProcessor: MessagePostProcessor
) {

    @Bean
    fun orderConnectionFactory() = CachingConnectionFactory()

    @Bean
    fun distributionRabbitAdmin(orderConnectionFactory: ConnectionFactory) = RabbitAdmin(orderConnectionFactory)

    @Bean
    fun orderRabbitQueue(): Queue = Queue("retailer_queue", false)

    @Bean
    fun topicRabbitExchange(): TopicExchange = TopicExchange("distributor_exchange")

    @Bean
    fun rabbitBinding(
        orderRabbitQueue: Queue,
        topicRabbitExchange: TopicExchange
    ): Binding = BindingBuilder
        .bind(orderRabbitQueue)
        .to(topicRabbitExchange)
        .with("retailer.AntonyKon.#")

    @Bean
    fun orderRabbitTemplate(
        connectionFactory: ConnectionFactory,
        messageConverter: Jackson2JsonMessageConverter
    ) = RabbitTemplate(connectionFactory).apply {
        setExchange("distributor_exchange")
        setBeforePublishPostProcessors(messagePostProcessor)
        setMessageConverter(messageConverter)
    }

    @Bean
    fun orderMessageConverter() =
        Jackson2JsonMessageConverter(objectMapper)
}

@Component
class OrderMessagePostProcessor : MessagePostProcessor {
    override fun postProcessMessage(message: Message): Message = message.apply {
        messageProperties.setHeader("Notify-Exchange", "distributor_exchange")
        messageProperties.setHeader("Notify-RoutingKey", "retailer.AntonyKon")
    }.also {
        println(message)
    }
}