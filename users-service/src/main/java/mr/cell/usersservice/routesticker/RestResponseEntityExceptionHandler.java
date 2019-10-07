package mr.cell.usersservice.routesticker;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO rewrite to use new date api
    private static final DateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSZ");

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFound(final ResourceNotFoundException ex,
                                                         final HttpServletRequest request,
                                                         final WebRequest webRequest) {
        final ErrorMessage errorMessage = new ErrorMessage(
                "Resource not found. Resource name: '" + ex.getResourceClassSimpleName() +
                        "', resource id: '" + ex.getResourceId() + "'",
                SIMPLE_DATE_FORMAT.format(new Date()));
        final HttpHeaders httpHeaders = createBaseHttpHeaders();
        return handleExceptionInternal(ex, errorMessage, httpHeaders, HttpStatus.NOT_FOUND, webRequest);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex,
                                                                 final HttpServletRequest request,
                                                                 final WebRequest webRequest) {
        final ErrorMessage errorMessage = new ErrorMessage("", SIMPLE_DATE_FORMAT.format(new Date()));
        final HttpHeaders httpHeaders = createBaseHttpHeaders();
        return handleExceptionInternal(ex, errorMessage, httpHeaders, HttpStatus.BAD_REQUEST, webRequest);
    }

    private HttpHeaders createBaseHttpHeaders() {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE);
        return httpHeaders;
    }
}
