package global.search.pretask.api.kakao.impl

import global.search.pretask.api.kakao.KakaoApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class KakaoApiImpl(
    private val webClient: WebClient,
    @Value("\${app.kakao.api.domain}")
    private val domain: String,
    @Value("\${app.kakao.api.api-key}")
    private val apiKey: String,
): KakaoApi {

    override fun searchBlog(query: String, sort: String, page: Int, size: Int): Mono<String> {
        return webClient.mutate().baseUrl(domain).build()
            .method(HttpMethod.GET)
            .uri { uriBuilder ->
                uriBuilder
                    .queryParam("query", query)
                    .queryParam("sort", sort)
                    .queryParam("page", page)
                    .queryParam("size", size)
                    .build()
            }
            .header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_PREFIX + apiKey)
            .retrieve()
            .bodyToMono(String::class.java)
    }

    companion object {
        const val AUTHORIZATION_PREFIX = "KakaoAK "
    }

}