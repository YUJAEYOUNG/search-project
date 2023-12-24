package global.search.pretask.domain.model.common.transfer.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "공통 HttpResponse 객체")
data class HttpResponse<T>(
    @Schema(description = "에러발생 했을 때의 표현객체")
    val error: ErrorMessage?,
    @Schema(description = "요청 성공시의 응답 본문")
    val payload: T
) {
    companion object {
        @JvmStatic
        fun <T> ofSuccess(payload: T) = HttpResponse(error = null, payload = payload)
        @JvmStatic
        fun <T> ofSuccess(payload: PagingResponse<T>) = HttpResponse(error = null, payload = payload)
        @JvmStatic
        fun ofFail(error: ErrorMessage) = HttpResponse<Any?>(error = error, payload = null)
    }
}