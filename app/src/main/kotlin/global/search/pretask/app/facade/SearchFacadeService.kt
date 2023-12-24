package global.search.pretask.app.facade

import global.search.pretask.domain.model.common.transfer.response.PagingMetaInfo
import global.search.pretask.domain.model.common.transfer.response.PagingResponse
import global.search.pretask.domain.model.search.entity.SearchKeyword
import global.search.pretask.domain.model.search.transfer.request.BlogSearchRequest
import global.search.pretask.domain.model.search.transfer.request.KeywordSearchRequest
import global.search.pretask.domain.model.search.transfer.response.BlogSearchResponse
import global.search.pretask.domain.model.search.transfer.response.KeywordSearchResponse
import global.search.pretask.service.search.SearchService
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class SearchFacadeService(
    private val searchService: SearchService,
) {

    @Transactional(propagation = Propagation.REQUIRED)
    fun searchBlog(
        blogSearchRequest: BlogSearchRequest
    ): PagingResponse<BlogSearchResponse> {
        val resultPage: PageImpl<BlogSearchResponse> = searchService.searchBlog(blogSearchRequest)
        return PagingResponse.of(contents = resultPage.content, meta = PagingMetaInfo.pageToMeta(resultPage))
    }

    @Transactional(readOnly = true)
    fun getPopularSearchKeyword(): List<Map<String, String>> {
        val result: List<Pair<String, String>> = searchService.getRankKeyword(
            start = 0,
            end = 9 // 문제상 max 10개
        )
        return result.map { (keyword, count) ->
            mapOf("keyword" to keyword, "count" to count.toDouble().toLong().toString()) // score 정수로 치환
        }
    }

    @Transactional(readOnly = true)
    fun getSearchKeywordList(keywordSearchRequest: KeywordSearchRequest): PagingResponse<KeywordSearchResponse> {
        val resultPage: PageImpl<SearchKeyword> = searchService.getSearchKeywordList(keywordSearchRequest)
        val contents = resultPage.content.map { KeywordSearchResponse(keyword = it.keyword, count = it.count) }
        return PagingResponse.of(contents = contents, meta = PagingMetaInfo.pageToMeta(resultPage))
    }
}