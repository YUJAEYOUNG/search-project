package global.search.pretask.domain.model.search.transfer.request

import org.springframework.data.domain.Pageable

data class KeywordSearchRequest(
    val keyword: String? = null,
    /** 페이징 **/
    val pageable: Pageable,
)
