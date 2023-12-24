package global.search.pretask.persistence.redis

interface SearchKeywordRedisRepository {
    fun save(keyword: String, count: Long)

    fun increaseCount(keyword: String)

    fun getRankKeyword(start: Long, end: Long): List<Pair<String, String>>
}