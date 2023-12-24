package global.search.pretask.domain.model.common.transfer.response

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.domain.PageImpl

@Schema(description = "페이징 메타 정보")
data class PagingMetaInfo(
    @Schema(description = "페이지 당 뿌려줄 아이템 갯수 ")
    val itemsPerPage: Int,
    @Schema(description = "검색된 전체 리스트 count")
    val totalItems: Int,
    @Schema(description = "현재 페이지")
    val currentPage: Int,
    @Schema(description = "검색된 전체 리스트 페이지 사이즈")
    val totalPages: Int,
) {
    companion object {

        /**
         * page 객체를 PagingMetaInfo로 변환
         */
        fun <T> pageToMeta(pageImpl: PageImpl<T>) = PagingMetaInfo(
            itemsPerPage = pageImpl.size,
            totalItems = pageImpl.totalElements.toInt(),
            currentPage = pageImpl.pageable.pageNumber,
            totalPages = pageImpl.totalPages,
        )
    }
}