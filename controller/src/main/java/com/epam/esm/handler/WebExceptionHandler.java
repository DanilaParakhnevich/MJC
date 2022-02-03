package com.epam.esm.handler;

import com.epam.esm.handler.error.ErrorCode;
import com.epam.esm.handler.error.ErrorResponse;
import com.epam.esm.handler.exception.BadParameterException;
import com.epam.esm.handler.translator.Translator;
import com.epam.esm.validator.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WebExceptionHandler {
    @Autowired
    private Translator translator;

    @ExceptionHandler(DuplicateTagException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(DuplicateTagException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.DUPLICATE_TAG.getCode()),
                translator.translate(e.getMessage())));
    }

    @ExceptionHandler(InvalidTagDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(InvalidTagDataException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_TAG.getCode()),
                getFullMessage(e.getMessage())));
    }

    @ExceptionHandler(UnknownTagException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle(UnknownTagException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.NOT_FOUND.value(), ErrorCode.NONEXISTENT_TAG.getCode()),
                getFullMessage(e.getMessage())));
    }

    @ExceptionHandler(DuplicateCertificateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(DuplicateCertificateException e) {
        return ResponseEntity.ok(new ErrorResponse(ErrorCode.DUPLICATE_CERTIFICATE.getCode(),
                translator.translate(e.getMessage())));
    }

    @ExceptionHandler(InvalidCertificateDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(InvalidCertificateDataException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_CERTIFICATE.getCode()),
                getFullMessage(e.getMessage())));
    }

    @ExceptionHandler(UnknownCertificateException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle(UnknownCertificateException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.NOT_FOUND.value(), ErrorCode.NONEXISTENT_CERTIFICATE.getCode()),
                getFullMessage(e.getMessage())));
    }

    @ExceptionHandler(InvalidDateFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle(InvalidDateFormatException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.INVALID_DATE_FORMAT.getCode()),
                translator.translate(e.getMessage())));
    }

    @ExceptionHandler(BadNameException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle(BadNameException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_VALUE.getCode()),
                getFullMessage(e.getMessage())));
    }

    @ExceptionHandler(BadDurationException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle(BadDurationException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_VALUE.getCode()),
                getFullMessage(e.getMessage())));
    }

    @ExceptionHandler(BadPriceException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle(BadPriceException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_VALUE.getCode()),
                getFullMessage(e.getMessage())));
    }

    @ExceptionHandler(BadDescriptionException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handle(BadDescriptionException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_VALUE.getCode()),
                getFullMessage(e.getMessage())));
    }

    @ExceptionHandler(BadParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle (BadParameterException e) {
        return ResponseEntity.ok(new ErrorResponse(
                concatenate(HttpStatus.BAD_REQUEST.value(), ErrorCode.BAD_PARAM.getCode()),
                translator.translate(e.getMessage())));
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handle (NumberFormatException e) {
        return ResponseEntity.ok(new ErrorResponse(
               HttpStatus.BAD_REQUEST.value(),
                e.getMessage()));
    }

    private String getFullMessage(String errorMessage) {
        return translator.translate(errorMessage.split("/")[0]) +
                getAdvanceToMessage(errorMessage
                        .split("/")[1]);
    }

    private String getAdvanceToMessage(String partOfError) {
        String finalPart = partOfError.split("=")[0];
        finalPart = "(" + finalPart + " = ";
        return " " + finalPart + partOfError.split("=")[1] + ")";
    }

    private long concatenate (long first, long second) {
        return Long.parseLong(String.valueOf(first) + String.valueOf(second));
    }

    public void setTranslator(Translator translator) {
        this.translator = translator;
    }
}
