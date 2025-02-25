package com.project.shopapp.exceptions;

import com.project.shopapp.models.ProductImage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "user not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    INVALID_JWT_PROCESSING(
            1008,
            "Failed to process the JWT token due to cryptographic or format issues. Ensure the token is correctly signed and encrypted.",
            HttpStatus.BAD_REQUEST),
    INPUT_DATA_PARSE_ERROR(
            1009,
            "Unable to parse the input data. Please ensure the format is correct and matches the expected pattern.",
            HttpStatus.BAD_REQUEST),
    INVALID_DOB(1010, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    LOGIN_REQUIRED(1011, "You need to log in to perform this action.", HttpStatus.UNAUTHORIZED),
    PRODUCT_NOT_FOUND(1012, "product not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1013, "category not found", HttpStatus.NOT_FOUND),
    MAXIMUM_IMAGES_PER_PRODUCT(1013, "You can only upload maximum "
            + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT + " images", HttpStatus.PAYLOAD_TOO_LARGE),
    PAYLOAD_TOO_LARGE(1014, "File is too large! Maximum size is 10MB", HttpStatus.PAYLOAD_TOO_LARGE),
    UNSUPPORTED_MEDIA_TYPE(1015, "File must be an image", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    ORDER_NOT_FOUND(1016, "Order not found", HttpStatus.NOT_FOUND),
    ORDER_DETAIL_NOT_FOUND(1016, "Order detail not found", HttpStatus.NOT_FOUND),
    SHIPPING_DATE_INVALID(1017,"Date must be at least today", HttpStatus.BAD_REQUEST)
    ;
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
