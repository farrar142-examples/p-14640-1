package com.example.demo.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionhandler: ResponseEntityExceptionHandler() {
	data class ErrorResponse(
		val statusCode: Int,
		val message: String?
	)

	@ExceptionHandler(HttpRuntimeException::class)
	fun handleHttpRuntimeException(ex: HttpRuntimeException): ResponseEntity<ErrorResponse>{
		return ResponseEntity.status(ex.statusCode).body(
			ErrorResponse(
				statusCode = ex.statusCode,
				message = ex.message
			)
		)
	}
}