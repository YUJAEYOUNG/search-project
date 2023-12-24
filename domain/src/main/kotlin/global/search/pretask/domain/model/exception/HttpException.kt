package global.search.pretask.domain.model.exception

import global.search.pretask.domain.model.exception.type.ErrorMessageType
import org.springframework.http.HttpStatus
import org.springframework.web.client.HttpStatusCodeException

class HttpException(
    val code: Int,
    val statusCode: HttpStatus,
    override val message: String,
): HttpStatusCodeException(statusCode, message) {

    constructor(errorType: ErrorMessageType, message: String = errorType.message): this(
        code = errorType.code,
        message = message,
        statusCode = errorType.status
    )

    override fun toString(): String {
        return "${statusCode}:E-${code}:${message}"
    }

}