package ueh.congdunghzz.aws.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ueh.congdunghzz.aws.common.util.ResponseUtil;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApplicationExceptionHandler {

    @ExceptionHandler(value = ApplicationException.class)
    public ResponseEntity<?> applicationExceptionHandler(ApplicationException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getCode(),
                exception.getStatus().getReasonPhrase(),
                exception.getMessage(),
                System.currentTimeMillis());
        return ResponseUtil.createResponse(exception.getStatus(), exception.getStatus().getReasonPhrase(), errorResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> badRequestParamExceptionHandler(MethodArgumentNotValidException exception){
        List<String> errorList = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        StringBuilder message = new StringBuilder();
        for (String item: errorList) {
            message.append(item).append(", ");
        }
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message.toString(),
                System.currentTimeMillis());
        return ResponseUtil.createResponse(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnCatchException(Exception exception){
        log.error("Un-Catch exception: ", exception);
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                "Please contact System Admin to resolve problem",
                System.currentTimeMillis());
        return ResponseUtil.createResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), response);
    }

    @ExceptionHandler(value = InternalAuthenticationServiceException.class)
    public ResponseEntity<?> handleInternalAuthenticationException(InternalAuthenticationServiceException ex) {
        if (ex.getCause() instanceof ApplicationException appException) {
            ErrorResponse errorResponse = new ErrorResponse(
                    appException.getCode(),
                    appException.getStatus().getReasonPhrase(),
                    appException.getMessage(),
                    System.currentTimeMillis()
            );
            return ResponseUtil.createResponse(appException.getStatus(), appException.getStatus().getReasonPhrase(), errorResponse);
        }
        return handleUnCatchException(ex);
    }
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<?> applicationExceptionHandler(HttpMessageNotReadableException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                400,
                "Bad Request",
                "Required request body is missing",
                System.currentTimeMillis());
        return ResponseUtil.createResponse(HttpStatus.BAD_REQUEST, "Bad Request", errorResponse);
    }

}
