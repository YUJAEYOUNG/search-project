package global.search.pretask.persistence.jpa

import global.search.pretask.domain.model.search.entity.SearchKeyword
import global.search.pretask.persistence.jpa.custom.SearchKeywordRepositoryCustom
import org.springframework.data.jpa.repository.JpaRepository

interface SearchKeywordRepository: JpaRepository<SearchKeyword, Long>, SearchKeywordRepositoryCustom {
    fun findByKeyword(keyword: String): SearchKeyword?
}