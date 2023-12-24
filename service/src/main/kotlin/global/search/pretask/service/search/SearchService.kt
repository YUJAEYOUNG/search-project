package global.search.pretask.service.search

import global.search.pretask.domain.model.search.entity.SearchKeyword
import global.search.pretask.domain.model.search.transfer.request.BlogSearchRequest
import global.search.pretask.domain.model.search.transfer.request.KeywordSearchRequest
import global.search.pretask.domain.model.search.transfer.response.BlogSearchResponse
import org.springframework.data.domain.PageImpl

interface SearchService {
    fun searchBlog(blogSearchRequest: BlogSearchRequest): PageImpl<BlogSearchResponse>

    fun recordSearchKeyword(keyword: String): SearchKeyword

    fun getSearchKeyword(keyword: String): SearchKeyword?

    fun getRankKeyword(start: Long, end: Long): List<Pair<String, String>>

    fun getSearchKeywordList(keywordSearchRequest: KeywordSearchRequest): PageImpl<SearchKeyword>
}