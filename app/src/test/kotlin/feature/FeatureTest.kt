package feature

import com.fasterxml.jackson.databind.ObjectMapper
import global.search.pretask.api.kakao.KakaoApi
import global.search.pretask.api.naver.NaverApi
import global.search.pretask.persistence.redis.SearchKeywordRedisRepository
import io.mockk.mockk
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(
    properties = [
        "spring.jpa.show-sql=true",
    ],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("featuretest")
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Transactional
@Import(FeatureTest.Companion.MockkServiceConfiguration::class)
abstract class FeatureTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @PersistenceContext
    lateinit var em: EntityManager

    companion object {

        @TestConfiguration
        class MockkServiceConfiguration {

            @Bean
            @Primary
            fun kakaoApi() = mockk<KakaoApi>()

            @Bean
            @Primary
            fun naverApi() = mockk<NaverApi>()

            @Bean
            @Primary
            fun searchKeywordRedisRepository() = mockk<SearchKeywordRedisRepository>()
        }
    }
}