package global.search.pretask.domain.model.external.kakao.transfer.response

import com.fasterxml.jackson.annotation.JsonProperty

data class Meta(
    @JsonProperty("is_end")
    val isEnd: Boolean,
    @JsonProperty("pageable_count")
    val pageableCount: Int,
    @JsonProperty("total_count")
    val totalCount: Int
)
