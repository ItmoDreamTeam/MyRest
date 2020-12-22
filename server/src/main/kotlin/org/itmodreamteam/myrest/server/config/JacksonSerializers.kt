package org.itmodreamteam.myrest.server.config

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import kotlinx.datetime.LocalDateTime
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class JacksonSerializersConfiguration {
    @Bean
    open fun jacksonSerializersModule(): Module = SimpleModule().also {
        it
            .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer)
            .addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer)
    }
}

object LocalDateTimeSerializer : StdSerializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun serialize(value: LocalDateTime, generator: JsonGenerator, serializerProvider: SerializerProvider) {
        generator.writeString(value.toString())
    }
}

object LocalDateTimeDeserializer : StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): LocalDateTime {
        return LocalDateTime.parse(parser.valueAsString)
    }
}
