package global.search.pretask.domain.model.search.transfer.request

import global.search.pretask.domain.model.search.type.SortType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "블로그 검색 요청 객체", enumAsRef = true)
data class BlogSearchRequest(
    @NotBlank
    val query: String,
    @Schema(description = "정렬 타입", enumAsRef = true)
    val sort: SortType? = SortType.ACCURACY,
    val page: Int? = 1,
    val size: Int? = 10,
)