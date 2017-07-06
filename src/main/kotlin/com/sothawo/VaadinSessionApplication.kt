package com.sothawo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import org.vaadin.spring.session.redis.VaadinSessionRewriteFilter

@SpringBootApplication
@EnableRedisHttpSession
class VaadinSessionApplication {

    @Bean
    fun vaadinSessionRewriteFilter() = VaadinSessionRewriteFilter()

}

fun main(args: Array<String>) {
    SpringApplication.run(VaadinSessionApplication::class.java, *args)
}
