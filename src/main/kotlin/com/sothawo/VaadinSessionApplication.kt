package com.sothawo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class VaadinSessionApplication

fun main(args: Array<String>) {
    SpringApplication.run(VaadinSessionApplication::class.java, *args)
}
