package global.search.pretask.domain.model.common.transfer.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "에러 response")
data class ErrorMessage(
    @Schema(description = "에러 코드")
    val code: Int,
    @Schema(description = "에러 메세지")
    val message: String
)