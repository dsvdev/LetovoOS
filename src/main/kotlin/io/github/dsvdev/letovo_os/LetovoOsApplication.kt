package io.github.dsvdev.letovo_os

import io.github.dsvdev.letovo_os.config.YamlPropertySourceFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@PropertySource(value = ["classpath:application.yml"], factory = YamlPropertySourceFactory::class)
@SpringBootApplication
class LetovoOsApplication

fun main(args: Array<String>) {
	runApplication<LetovoOsApplication>(*args)
}
