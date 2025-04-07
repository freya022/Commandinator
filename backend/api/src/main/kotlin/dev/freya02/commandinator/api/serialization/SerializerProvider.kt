package dev.freya02.commandinator.api.serialization

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SerializerProvider {

    @Bean
    fun kotlinxJson(): Json = Json
}