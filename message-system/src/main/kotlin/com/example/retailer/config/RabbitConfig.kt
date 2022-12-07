package com.example.retailer.config

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Declarables
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RabbitConfig {

    @Autowired
    private lateinit var config: RetailerConfig

    @Bean
    fun topicBindings(): Declarables? {
        //exchange = distributor_exchange,
        //routingKey = distributor.placeOrder.{githubUsername}.{orderId}

        val queue = Queue(config.queueName, false)
        val topicExchange = TopicExchange(config.topicExchangeName)

        return Declarables(
            queue,
            topicExchange,
            BindingBuilder.bind(queue).to(topicExchange).with(config.inputRoutingKey)
        )


//        val topicOut = Queue("distributor", )
//        val topicIn = Queue("order_queue", )
//
//        val topicExchange = TopicExchange("distributor_exchange")
//
//        return Declarables(
//            topicOut,
//            topicIn,
//            topicExchange,
//            BindingBuilder.bind(topicOut).to(topicExchange).with("distributor.placeOrder.${githubUsername}.*"),
//            BindingBuilder.bind(topicIn).to(topicExchange).with("retail.${githubUsername}.*")
//        )
    }

//    @Bean
//    fun jsonMessageConverter(): MessageConverter? {
//        return Jackson2JsonMessageConverter()
//    }
//
//    @Bean
//    fun rabbitListenerContainerFactory(): DirectRabbitListenerContainerFactory {
//        val factory = DirectRabbitListenerContainerFactory()
//        factory.setConnectionFactory(connectionFactory())
//        factory.setMessageConverter(jsonMessageConverter())
//        return factory
//    }
//
//    @Bean
//    fun connectionFactory(): CachingConnectionFactory? {
//        return CachingConnectionFactory("localhost")
//    }
}