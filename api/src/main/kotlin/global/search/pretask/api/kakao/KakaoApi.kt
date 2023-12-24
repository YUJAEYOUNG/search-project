package global.search.pretask.api.kakao

import reactor.core.publisher.Mono

interface KakaoApi {
    fun searchBlog(query: String, sort: String, page: Int, size: Int): Mono<String>
}