package global.search.pretask.persistence.redis.impl

import global.search.pretask.persistence.redis.SearchKeywordRedisRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.ZSetOperations
import org.springframework.stereotype.Repository

@Repository
class SearchKeywordRedisRepositoryImpl(
    @Qualifier("searchKeywordRedisTemplate")
    private val redisTemplate: RedisTemplate<String, String>
): SearchKeywordRedisRepository {

    override fun save(keyword: String, count: Long) {
        val opsForZSet = redisTemplate.opsForZSet()
        try {
            opsForZSet.add(KEYWORD_COUNT_PREFIX, keyword, count.toDouble())
        } catch (ex: Exception) {
            logger.error(ex.message, ex)
        }
    }

    override fun increaseCount(keyword: String) {
        val opsForZSet = redisTemplate.opsForZSet()
        try {
            opsForZSet.incrementScore(KEYWORD_COUNT_PREFIX, keyword, 1.0)
        } catch (ex: Exception) {
            logger.error(ex.message, ex)
        }
    }

    override fun getRankKeyword(start: Long, end: Long): List<Pair<String, String>> {
        val opsForZSet = redisTemplate.opsForZSet()
        var result = emptyList<Pair<String, String>>()
        try {
            val resultSet: MutableSet<ZSetOperations.TypedTuple<String>>? = opsForZSet.reverseRangeWithScores(KEYWORD_COUNT_PREFIX, start, end)
            result = resultSet?.map { typedTuple ->
                typedTuple.value!! to typedTuple.score.toString() }!!
        } catch (ex: Exception) {
            logger.error(ex.message, ex)
        }

        return result
    }

    companion object {
        const val KEYWORD_COUNT_PREFIX = "keyword:count:"
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}