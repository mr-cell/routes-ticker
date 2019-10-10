package mr.cell.routesticker.climbingroutesservice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.server.ServerWebExchange

@ControllerAdvice
class RestResponseEntityExceptionHandler {

    @ExceptionHandler
    fun handleIllegalArgumentException(ex: IllegalArgumentException, exchange: ServerWebExchange):
            ResponseEntity<ErrorMessage> {
        val path = exchange.request.path.value()
        val errorMessage = ErrorMessage(System.currentTimeMillis(), ex.message ?: "", path)
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleResourceNotFoundException(ex: ResourceNotFoundException, exchange: ServerWebExchange):
            ResponseEntity<ErrorMessage> {
        val path = exchange.request.path.value()
        val message = "Resource not found. Resource name: '${ex.resourceClassSimpleName}', " +
                "resource id: '${ex.resourceId}'"
        val errorMessage = ErrorMessage(System.currentTimeMillis(), message, path)
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}