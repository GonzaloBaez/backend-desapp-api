package ar.edu.unq.desapp.grupoG.backenddesappapi.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BadRequest::class)
    fun badRequestException(exception : BadRequest) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(exception: NotFoundException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DuplicateUniqueException::class)
    fun handleDuplicateValue(exception: DuplicateUniqueException) : ResponseEntity<Any>{
        return ResponseEntity(exception.message,HttpStatus.BAD_REQUEST)
    }
}