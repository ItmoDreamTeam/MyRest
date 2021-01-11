package org.itmodreamteam.myrest.server.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("storage")
class StorageProperties {
    var directory: String = "/storage"
}
