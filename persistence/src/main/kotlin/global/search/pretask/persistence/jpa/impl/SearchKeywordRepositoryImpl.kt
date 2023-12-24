package global.search.pretask.persistence.jpa.impl

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import global.search.pretask.domain.model.search.entity.QSearchKeyword
import global.search.pretask.domain.model.search.entity.SearchKeyword
import global.search.pretask.domain.model.search.transfer.request.KeywordSearchRequest
import global.search.pretask.persistence.jpa.custom.SearchKeywordRepositoryCustom
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Repository

@Repository
class SearchKeywordRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
): SearchKeywordRepositoryCustom {
    private val qSearchKeyword: QSearchKeyword = QSearchKeyword.searchKeyword

    override fun findSearchKeywordList(keywordSearchRequest: KeywordSearchRequest): PageImpl<SearchKeyword> {
        val query = this.makeWhereQuery(keywordSearchRequest)

        val pagedQuery =  query.clone()
            .select(qSearchKeyword)
            .orderBy(qSearchKeyword.id.desc())
            .offset(keywordSearchRequest.pageable.offset)
            .limit(keywordSearchRequest.pageable.pageSize.toLong())

        val content = pagedQuery.clone()
            .fetch()

        val count = query.clone()
            .select(qSearchKeyword.count())
            .fetchFirst()

        return PageImpl(content, keywordSearchRequest.pageable, count)
    }

    private fun makeWhereQuery(keywordSearchRequest: KeywordSearchRequest): JPAQuery<*> {
        return jpaQueryFactory.from(qSearchKeyword)
            .apply {
                where(
                    isEqAppGroupId(keywordSearchRequest.keyword),
                )
            }
    }

    private fun isEqAppGroupId(keyword: String?): BooleanExpression? {

        if (keyword.isNullOrBlank()) {
            return null
        }

        return qSearchKeyword.keyword.contains(keyword)
    }
}