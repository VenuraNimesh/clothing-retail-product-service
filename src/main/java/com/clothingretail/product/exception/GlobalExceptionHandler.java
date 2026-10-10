package com.clothingretail.product.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BrandNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBrandNotFound(
            BrandNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "BRAND_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFound(
            CategoryNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "CATEGORY_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(
            ProductNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "PRODUCT_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage()
                )
                .orElse("Invalid request");

        return buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                message,
                request.getRequestURI()
        );
    }

    @ExceptionHandler(SizeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSizeNotFound(
            SizeNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "SIZE_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ColorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleColorNotFound(
            ColorNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "COLOR_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateSku(
            DuplicateSkuException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "DUPLICATE_SKU",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(DuplicateProductVariantException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateVariant(
            DuplicateProductVariantException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "DUPLICATE_PRODUCT_VARIANT",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(ProductVariantNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductVariantNotFound(
            ProductVariantNotFoundException exception,
            HttpServletRequest request
    ) {
        return buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "PRODUCT_VARIANT_NOT_FOUND",
                exception.getMessage(),
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String error,
            String message,
            String path
    ) {
        ErrorResponse response = new ErrorResponse(
                Instant.now(),
                status.value(),
                error,
                message,
                path
        );

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
