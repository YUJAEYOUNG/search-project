package global.search.pretask.app.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import io.netty.resolver.DefaultAddressResolverGroup
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.client.reactive.ReactorResourceFactory
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig {

    @Bean
    fun resourceFactory() = ReactorResourceFactory().apply {
        isUseGlobalResources = false // (1)
    }

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        val httpClient = HttpClient.create()
                .resolver(DefaultAddressResolverGroup.INSTANCE)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected { conn -> conn
                    .addHandlerLast(ReadTimeoutHandler(5))
                    .addHandlerLast(WriteTimeoutHandler(5))
                }

        return builder.codecs {
            it.defaultCodecs().maxInMemorySize(1024 * 1024 * 4)
        }.clientConnector(ReactorClientHttpConnector(httpClient)).build()
    }
}