package com.ahmad.carts.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class ErrorResponseDto {
    private HttpStatus errorCode;
    private String errorMessage;
    private String apiPath;
    private LocalDateTime errorTime;
}
