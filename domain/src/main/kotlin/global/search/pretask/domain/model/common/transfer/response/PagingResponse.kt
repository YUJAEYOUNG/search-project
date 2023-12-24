package global.search.pretask.domain.model.common.transfer.response

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Http Response 페이징용 객체")
data class PagingResponse<T>(
    val contents: List<T>,
    val meta: PagingMetaInfo,
) {
    companion object {
        @JvmStatic
        fun <T> of(contents: List<T>, meta: PagingMetaInfo) = PagingResponse(contents = contents, meta = meta)
    }
}