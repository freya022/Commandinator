package dev.freya02.commandinator.api.exceptions

import kotlinx.serialization.Serializable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@Serializable
data class ExceptionDTO(val error: String)

fun exceptionResponse(status: HttpStatus, message: String? = null): ResponseEntity<ExceptionDTO> = ResponseEntity.status(status).body(message?.let(::ExceptionDTO))