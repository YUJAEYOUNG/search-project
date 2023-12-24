package global.search.pretask.app.exception

import global.search.pretask.domain.model.common.transfer.response.ErrorMessage
import global.search.pretask.domain.model.common.transfer.response.HttpResponse
import global.search.pretask.domain.model.exception.HttpException
import global.search.pretask.domain.model.exception.type.ErrorMessageType
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(HttpException::class)
    fun handleHttpException(e: HttpException): ResponseEntity<HttpResponse<Any?>> {
        return ResponseEntity
            .status(e.statusCode)
            .contentType(MediaType.APPLICATION_JSON)
            .body(HttpResponse.ofFail(ErrorMessage(e.code, e.message)))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<HttpResponse<Any?>> {
        val error = ErrorMessage(
            code = ErrorMessageType.INVALID_REQUEST.code,
            message = e.bindingResult.fieldError?.defaultMessage ?: ErrorMessageType.INVALID_REQUEST.message
        )
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(HttpResponse.ofFail(error))
    }

    @ExceptionHandler(Throwable::class)
    fun handleThrowable(cause: Throwable?): ResponseEntity<*>? {
        val error = ErrorMessage(
            code = ErrorMessageType.INTERNAL_SERVER_ERROR.code,
            message = ErrorMessageType.INTERNAL_SERVER_ERROR.message
        )
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .contentType(MediaType.APPLICATION_JSON)
            .body(HttpResponse.ofFail(error))
    }

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }
}
