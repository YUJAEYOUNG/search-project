package global.search.pretask.persistence.jpa.custom

import global.search.pretask.domain.model.search.entity.SearchKeyword
import global.search.pretask.domain.model.search.transfer.request.KeywordSearchRequest
import org.springframework.data.domain.PageImpl

interface SearchKeywordRepositoryCustom {
    fun findSearchKeywordList(keywordSearchRequest: KeywordSearchRequest): PageImpl<SearchKeyword>
}