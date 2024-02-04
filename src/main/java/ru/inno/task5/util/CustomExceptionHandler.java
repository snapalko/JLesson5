package ru.inno.task5.util;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.inno.task5.exception.BadRequestException;
import ru.inno.task5.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handle(BadRequestException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseError handle(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseError(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        final List<Violation> violations = e.getConstraintViolations().stream()
                .map(
                        violation -> new Violation(
                                violation.getPropertyPath().toString(),
                                violation.getMessage()/*, null*/
                        ))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
//    public ResponseExceptionClass validationErrorHandler(MethodArgumentNotValidException e) {
//        FieldError fieldError = e.getBindingResult().getFieldError();
//        StackTraceElement[] stack = e.getStackTrace();
//        assert fieldError != null;
//        return new ResponseExceptionClass(fieldError.getDefaultMessage(), stack);
//    }
    public ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
//        FieldError fieldError = e.getBindingResult().getFieldError();
//        StackTraceElement[] stack = e.getStackTrace();
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(
                        error -> new Violation(
                                error.getField(),
                                error.getDefaultMessage()/*, stack*/))
                .collect(Collectors.toList());
        return new ValidationErrorResponse(violations);
    }
}
