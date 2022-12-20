package com.example.retailer.configs

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
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@EnableRabbit
@Configuration
class AppConfig {

    @Bean
    fun queue(): Queue {
        return Queue("retailer.queue")
    }

    @Bean
    fun topicExchange(): TopicExchange? {
        return TopicExchange("distributor_exchange")
    }

    @Bean
    fun binding(): Binding {
        return BindingBuilder
            .bind(queue())
            .to(topicExchange())
            .with("retailer.kulinichrs")
    }

    @Bean
    fun jsonMessageConverter(): Jackson2JsonMessageConverter {
        return Jackson2JsonMessageConverter()
    }

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate? {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jsonMessageConverter()
        rabbitTemplate.setExchange("distributor_exchange")
        return rabbitTemplate
    }
}