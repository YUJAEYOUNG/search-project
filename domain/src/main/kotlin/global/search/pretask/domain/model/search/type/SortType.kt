package global.search.pretask.domain.model.search.type

import io.swagger.v3.oas.annotations.media.Schema

enum class SortType(
    val code: String
) {
    @Schema(description = "정확도순")
    ACCURACY("ACCURACY"),

    @Schema(description = "최신순")
    RECENCY("RECENCY");

    companion object {
        operator fun invoke(code: String) = valueOf(code.uppercase())
    }
}