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

//    @Bean
//    fun container(
//        connectionFactory: ConnectionFactory?,
//        listenerAdapter: MessagingMessageListenerAdapter?
//    ): DirectMessageListenerContainer? {
//        val container = DirectMessageListenerContainer()
//        container.connectionFactory = connectionFactory!!
//        container.setQueueNames(config.queueName)
//        container.setMessageListener(listenerAdapter!!)
//
//
//        //log.info("Build listener container")
//        return container
//    }
//
//    @Bean
//    fun listenerAdapter(receiver: RetailerConsumer?): MessagingMessageListenerAdapter? {
//        val adapter = MessagingMessageListenerAdapter()
//        adapter.setHandlerAdapter(HandlerAdapter(InvocableHandlerMethod(receiver!!, receiver!!.javaClass.getMethod("listen", Message::class.java))))
//        return adapter
//    }
//
//    @Bean
//    fun queue(): Queue? {
//        return Queue(config.queueName, false)
//    }
//
//    @Bean
//    fun exchange(): TopicExchange? {
//        return TopicExchange(config.topicExchangeName)
//    }
//
//    @Bean
//    fun binding(queue: Queue?, exchange: TopicExchange?): Binding? {
//        return BindingBuilder.bind(queue).to(exchange).with("${config.inputRoutingKey}.#")
//    }


    @Bean
    fun topicBindings(): Declarables? {
        val queue = Queue(config.queueName, false)
        val topicExchange = TopicExchange(config.topicExchangeName)

        return Declarables(
            queue,
            topicExchange,
            BindingBuilder.bind(queue).to(topicExchange).with("${config.inputRoutingKey}.#")
        )
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