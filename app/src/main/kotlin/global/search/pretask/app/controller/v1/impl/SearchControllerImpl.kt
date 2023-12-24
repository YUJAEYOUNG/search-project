package global.search.pretask.app.controller.v1.impl

import global.search.pretask.app.facade.SearchFacadeService
import global.search.pretask.domain.model.common.transfer.response.HttpResponse
import global.search.pretask.domain.model.common.transfer.response.PagingResponse
import global.search.pretask.domain.model.search.transfer.request.BlogSearchRequest
import global.search.pretask.domain.model.search.transfer.request.KeywordSearchRequest
import global.search.pretask.domain.model.search.transfer.response.BlogSearchResponse
import global.search.pretask.domain.model.search.transfer.response.KeywordSearchResponse
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/search")
class SearchControllerImpl(
    private val searchFacadeService: SearchFacadeService,
) {

    @GetMapping("/blog")
    fun searchBlog(
        @ParameterObject blogSearchRequest: BlogSearchRequest
    ): ResponseEntity<HttpResponse<PagingResponse<BlogSearchResponse>>> {
        val response = searchFacadeService.searchBlog(blogSearchRequest)
        return ResponseEntity.ok().body(HttpResponse.ofSuccess(response))
    }

    @GetMapping("/popular-keyword")
    fun getPopularSearchKeyword(): ResponseEntity<HttpResponse<List<Map<String, String>>>> {
        val response = searchFacadeService.getPopularSearchKeyword()
        return ResponseEntity.ok().body(HttpResponse.ofSuccess(response))
    }

    @GetMapping("/keyword-list")
    fun getSearchKeywordList(
        @RequestParam(required = false) keyword: String?,
        @ParameterObject @PageableDefault(size = 10, page = 0) pageable: Pageable,
    ): ResponseEntity<HttpResponse<PagingResponse<KeywordSearchResponse>>> {
        val keywordSearchRequest = KeywordSearchRequest(keyword = keyword, pageable = pageable)
        val response = searchFacadeService.getSearchKeywordList(keywordSearchRequest)
        return ResponseEntity.ok().body(HttpResponse.ofSuccess(response))
    }
}