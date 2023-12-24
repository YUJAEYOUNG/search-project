package feature.feature

import feature.FeatureTest
import global.search.pretask.api.kakao.KakaoApi
import global.search.pretask.api.naver.NaverApi
import global.search.pretask.domain.model.external.kakao.transfer.response.Document
import global.search.pretask.domain.model.external.kakao.transfer.response.KakaoBlogSearchResponse
import global.search.pretask.domain.model.external.kakao.transfer.response.Meta
import global.search.pretask.domain.model.external.naver.transfer.response.Item
import global.search.pretask.domain.model.external.naver.transfer.response.NaverBlogSearchResponse
import global.search.pretask.persistence.redis.SearchKeywordRedisRepository
import io.mockk.every
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import reactor.core.publisher.Mono

class SearchFeatureTest: FeatureTest() {
    private val baseUrl = "/v1/search"

    @Autowired
    lateinit var kakaoApi: KakaoApi

    @Autowired
    lateinit var naverApi: NaverApi

    @Autowired
    lateinit var searchKeywordRedisRepository: SearchKeywordRedisRepository

    @Nested
    @DisplayName("조회 테스트")
    inner class SearchTest {

        @Test
        @DisplayName("블로그 조회 실패 - query 필수값 없을 경우")
        fun searchBlogFailTestByInavlidRequest() {
            // When & Then
            mockMvc.perform(get("$baseUrl/blog"))
                .andExpect(status().isBadRequest)
        }

        @Test
        @DisplayName("블로그 조회 실패 - enum type 불일치")
        fun searchBlogFailTestByInavlidRequest2() {
            // When & Then
            mockMvc.perform(get("$baseUrl/blog")
                    .queryParam("query", "test")
                    .queryParam("sort", "invalid-value") // enum type 불일치
                )
                .andExpect(status().isBadRequest)
        }

        @Test
        @DisplayName("블로그 조회 - by 카카오")
        fun searchBlogTestByKakao() {
            // Given
            val response = this.makeKakaoResponse()

            val jsonStr = objectMapper.writeValueAsString(response)

            every { kakaoApi.searchBlog(any(), any(), any(), any()) } returns Mono.just(jsonStr)
            every { searchKeywordRedisRepository.save(any(), any()) } returnsArgument 0
            every { searchKeywordRedisRepository.increaseCount(any()) } returnsArgument 0

            // When & Then
            mockMvc.perform(get("$baseUrl/blog").queryParam("query", "test"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.payload.contents[0].title").value("test-title"))
        }

        @Test
        @DisplayName("블로그 조회 - by 네이버")
        fun searchBlogTestByNaver() {
            // Given
            val response = this.makeNaverResponse()
            val jsonStr = objectMapper.writeValueAsString(response)

            // kakao exception 강제 발생 하여 Naver API 호출
            every { kakaoApi.searchBlog(any(), any(), any(), any()) } returns Mono.error { Exception() }
            every { naverApi.searchBlog(any(), any(), any(), any()) } returns Mono.just(jsonStr)
            every { searchKeywordRedisRepository.save(any(), any()) } returnsArgument 0
            every { searchKeywordRedisRepository.increaseCount(any()) } returnsArgument 0

            // When & Then
            mockMvc.perform(get("$baseUrl/blog").queryParam("query", "test"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.payload.contents[0].title").value("test-title-n"))
        }

        @Test
        @DisplayName("블로그 조회 실패 - naver & kakao api 호출 에러")
        fun searchBlogFailTestByExternalApiCallError() {
            // kakao exception 강제 발생 하여 Naver API 호출
            every { kakaoApi.searchBlog(any(), any(), any(), any()) } returns Mono.error { Exception() }
            every { naverApi.searchBlog(any(), any(), any(), any()) } returns Mono.error { Exception() }
            every { searchKeywordRedisRepository.save(any(), any()) } returnsArgument 0
            every { searchKeywordRedisRepository.increaseCount(any()) } returnsArgument 0

            // When & Then
            mockMvc.perform(get("$baseUrl/blog").queryParam("query", "test"))
                .andExpect(status().is5xxServerError)
        }

        @Test
        @DisplayName("인기 검색어 조회")
        fun getPopularKeywordTest() {
            // Given
            val mockkResult = listOf("test1" to "6.0", "test2" to "4.0", "test3" to "2.0")
            every { searchKeywordRedisRepository.getRankKeyword(any(), any()) } returns mockkResult

            // When & Then
            mockMvc.perform(get("$baseUrl/popular-keyword"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.payload[0].keyword").value("test1"))
                .andExpect(jsonPath("$.payload[1].keyword").value("test2"))
        }

        private fun makeKakaoResponse(): KakaoBlogSearchResponse {
            val document = Document(
                blogname = "test-blogname",
                contents = "test-contents",
                datetime = "2023-12-14T11:24:00.000+09:00",
                thumbnail = "test-thumbnail",
                title = "test-title",
                url = "https://blog.test"
            )
            val meta = Meta(isEnd = false, pageableCount = 100, totalCount = 200)
            return KakaoBlogSearchResponse(documents = listOf(document), meta = meta)
        }

        private fun makeNaverResponse(): NaverBlogSearchResponse {
            return NaverBlogSearchResponse(
                lastBuildDate = "Thu, 21 Dec 2023 22:54:52 +0900",
                total = 100,
                start = 1,
                display = 10,
                items = listOf(
                    Item(
                        title = "test-title-n",
                        link = "https://blog.test",
                        description = "test-description",
                        bloggername = "test-bloggername",
                        bloggerlink = "https://blog.test",
                        postdate = "20231214"
                    )
                )
            )
        }
    }
}