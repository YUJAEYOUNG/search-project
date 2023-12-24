package global.search.pretask.api.naver.impl

import global.search.pretask.api.naver.NaverApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class NaverApiImpl(
    private val webClient: WebClient,
    @Value("\${app.naver.api.domain}")
    private val domain: String,
    @Value("\${app.naver.api.client-id}")
    private val clientId: String,
    @Value("\${app.naver.api.client-secret}")
    private val clientSecret: String,
): NaverApi {

    override fun searchBlog(query: String, sort: String, display: Int, start: Int): Mono<String> {
        val sort = when (sort) {
            "accuracy" -> "sim"
            "recency" -> "date"
            else -> "sim" // default
        }

        return webClient.mutate().baseUrl(domain).build()
            .method(HttpMethod.GET)
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("query", query)
                    .queryParam("sort", sort)
                    .queryParam("display", display)
                    .queryParam("start", start)
                    .build()
            }
            .header(CLIENT_ID_HEADER, clientId)
            .header(CLIENT_SECRET_HEADER, clientSecret)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    companion object {
        const val CLIENT_ID_HEADER = "X-Naver-Client-Id"
        const val CLIENT_SECRET_HEADER = "X-Naver-Client-Secret"
    }

}