package global.search.pretask.api.naver

import reactor.core.publisher.Mono

interface NaverApi {
    fun searchBlog(query: String, sort: String, display: Int, start: Int): Mono<String>
}