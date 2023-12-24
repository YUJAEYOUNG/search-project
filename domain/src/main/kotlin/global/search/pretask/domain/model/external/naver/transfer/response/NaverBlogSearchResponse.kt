package global.search.pretask.domain.model.external.naver.transfer.response

data class NaverBlogSearchResponse(
    val items: List<Item>,
    val lastBuildDate: String,
    val start: Int,
    val display: Int,
    val total: Int
)