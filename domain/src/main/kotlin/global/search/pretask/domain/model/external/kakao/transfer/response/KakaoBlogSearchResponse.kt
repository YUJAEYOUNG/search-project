package global.search.pretask.domain.model.external.kakao.transfer.response

data class KakaoBlogSearchResponse(
    val documents: List<Document>,
    val meta: Meta
)