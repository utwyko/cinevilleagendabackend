package com.wykorijnsburger.movietimes.backend

import com.wykorijnsburger.movietimes.backend.utils.InvalidApikeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestMapping


@ControllerAdvice
@RequestMapping(produces = arrayOf("application/vnd.error+json"))
class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = InvalidApikeyException::class)
    fun handleConflict() = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(ProblemResponse(
                    title = "Invalid APIkey",
                    status = HttpStatus.UNAUTHORIZED.value(),
                    detail = null
            ))
}

data class ProblemResponse(
        val title: String,
        val status: Int,
        val detail: String?
)