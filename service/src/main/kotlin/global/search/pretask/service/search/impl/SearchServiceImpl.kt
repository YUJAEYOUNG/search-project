package global.search.pretask.service.search.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import global.search.pretask.api.kakao.KakaoApi
import global.search.pretask.api.naver.NaverApi
import global.search.pretask.common.utils.DateUtils
import global.search.pretask.domain.model.exception.HttpException
import global.search.pretask.domain.model.exception.type.ErrorMessageType
import global.search.pretask.domain.model.external.kakao.transfer.response.KakaoBlogSearchResponse
import global.search.pretask.domain.model.external.naver.transfer.response.NaverBlogSearchResponse
import global.search.pretask.domain.model.search.entity.SearchKeyword
import global.search.pretask.domain.model.search.transfer.request.BlogSearchRequest
import global.search.pretask.domain.model.search.transfer.request.KeywordSearchRequest
import global.search.pretask.domain.model.search.transfer.response.BlogSearchResponse
import global.search.pretask.persistence.jpa.SearchKeywordRepository
import global.search.pretask.persistence.redis.SearchKeywordRedisRepository
import global.search.pretask.service.search.SearchService
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class SearchServiceImpl(
    private val kakaoApi: KakaoApi,
    private val naverApi: NaverApi,
    private val objectMapper: ObjectMapper,
    private val searchKeywordRepository: SearchKeywordRepository,
    private val searchKeywordRedisRepository: SearchKeywordRedisRepository
): SearchService {

    override fun searchBlog(
        blogSearchRequest: BlogSearchRequest
    ): PageImpl<BlogSearchResponse> {
        // 현재는 kakao & naver 2개만 사용하지만 추후 다른 API 추가 될 시 Boolean 말고 다른 것으로 활용
        var isKakaoApi: Boolean = true
        var jsonStr: String? = null

        try {
            jsonStr = kakaoApi.searchBlog(
                query = blogSearchRequest.query,
                sort = blogSearchRequest.sort!!.code.lowercase(),
                page = blogSearchRequest.page!!,
                size = blogSearchRequest.size!!
            ).doOnError { e ->
                logger.error(e.message, e)
                throw HttpException(ErrorMessageType.KAKAOAPI_ERROR)
            }.block()
        } catch (e: HttpException) {

            if (e.code == ErrorMessageType.KAKAOAPI_ERROR.code) {
                isKakaoApi = false
            }
        }

        if (!isKakaoApi) {
            jsonStr = naverApi.searchBlog(
                query = blogSearchRequest.query,
                sort = blogSearchRequest.sort!!.code.lowercase(),
                start = blogSearchRequest.page!!,
                display = blogSearchRequest.size!!
            ).doOnError { e ->
                logger.error(e.message, e)
                throw HttpException(ErrorMessageType.EXTERNALAPI_ERROR)
            }.block()
        }

        val result: PageImpl<BlogSearchResponse> = when {
            isKakaoApi -> {
                this.convertKakaoResponseToPageImpl(
                    objectMapper.readValue<KakaoBlogSearchResponse>(jsonStr!!),
                    blogSearchRequest.page!!,
                    blogSearchRequest.size!!
                )
            }
            else -> {
                this.convertNaverResponseToPageImpl(
                    objectMapper.readValue<NaverBlogSearchResponse>(jsonStr!!)
                )
            }
        }

        // 검색어 저장
        this.recordSearchKeyword(blogSearchRequest.query)

        return result
    }

    override fun recordSearchKeyword(keyword: String): SearchKeyword {
        val searchKeyword = this.getSearchKeyword(keyword)

        if (searchKeyword != null) {
            searchKeyword.count += 1
            return searchKeywordRepository.save(searchKeyword).also {
                searchKeywordRedisRepository.increaseCount(keyword)
            }
        } else {
            return searchKeywordRepository.save(SearchKeyword(keyword = keyword, count = 1L)).also {
                searchKeywordRedisRepository.save(keyword, 1L)
            }
        }
    }

    override fun getSearchKeyword(keyword: String): SearchKeyword? {
        return searchKeywordRepository.findByKeyword(keyword)
    }

    override fun getRankKeyword(start: Long, end: Long): List<Pair<String, String>> {
        return searchKeywordRedisRepository.getRankKeyword(start, end)
    }

    override fun getSearchKeywordList(keywordSearchRequest: KeywordSearchRequest): PageImpl<SearchKeyword> {
        return searchKeywordRepository.findSearchKeywordList(keywordSearchRequest)
    }

    private fun convertKakaoResponseToPageImpl(
        kakaoResponse: KakaoBlogSearchResponse,
        page: Int,
        size: Int
    ): PageImpl<BlogSearchResponse> {
        val meta = kakaoResponse.meta
        val pageable = PageRequest.of(page, size)

        return PageImpl(
            kakaoResponse.documents.map {
                BlogSearchResponse(
                    title = it.title,
                    url = it.url,
                    blogname = it.blogname,
                    datetime = DateUtils.to(it.datetime),
                    summary = it.contents,
                )
            }, pageable, meta.pageableCount.toLong())
    }

    private fun convertNaverResponseToPageImpl(
        naverResponse: NaverBlogSearchResponse,
    ): PageImpl<BlogSearchResponse> {
        val pageable = PageRequest.of(naverResponse.start, naverResponse.display)

        return PageImpl(
            naverResponse.items.map {
                BlogSearchResponse(
                    title = it.title,
                    url = it.bloggerlink,
                    blogname = it.bloggername,
                    datetime = it.postdate,
                    summary = it.description,
                )
            }, pageable, naverResponse.total.toLong())
    }

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }
}