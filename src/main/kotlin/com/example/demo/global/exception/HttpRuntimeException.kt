package com.example.demo.global.exception

open class HttpRuntimeException(
	val statusCode:Int,
	message:String? = null,
	cause: Throwable? =null
): RuntimeException(message, cause)

open class DataNotFoundException(
	message:String? = null,
	cause: Throwable? =null
): HttpRuntimeException(404, message, cause)