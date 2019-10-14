package mr.cell.routesticker.climbingroutesservice

import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.reactive.function.server.ServerRequest


@ControllerAdvice // @ControllerAdvice instead of @Component to have it working with @WebFluxTest annotation
class GlobalExceptionWrapper: DefaultErrorAttributes(false) {

    override fun getErrorAttributes(request: ServerRequest?, includeStackTrace: Boolean): MutableMap<String, Any> {
        val errorAttributes = super.getErrorAttributes(request, false)
        val error = getError(request)
        errorAttributes.put("message", error.message ?: "")
        errorAttributes.put("timestamp", System.currentTimeMillis())
        errorAttributes.put("path", request?.path())
        errorAttributes.put("customAttr", "custom")

        return errorAttributes
    }

}