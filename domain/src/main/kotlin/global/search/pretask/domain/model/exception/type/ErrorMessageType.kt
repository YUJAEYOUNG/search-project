package global.search.pretask.domain.model.exception.type

import org.springframework.http.HttpStatus

/**
 * 에러 메세지 코드 및 타입 정의
 */
enum class ErrorMessageType(
    val code: Int,
    val message: String,
    val status: HttpStatus
) {
    INTERNAL_SERVER_ERROR(1000, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_REQUEST(1001, "Invalid Request", HttpStatus.BAD_REQUEST),

    // external-api 관련
    EXTERNALAPI_ERROR(10000, "External API Call Error", HttpStatus.INTERNAL_SERVER_ERROR),
    KAKAOAPI_ERROR(10001, "Kakao API Error", HttpStatus.INTERNAL_SERVER_ERROR),
//    NAVERAPI_ERROR(10002, "Naver API Error", HttpStatus.INTERNAL_SERVER_ERROR),
}