package com.ahmad.products.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Schema(
        name = "Error Response",
        description = "Holds error response info"
)
public class ErrorResponseDto {
    @Schema(example = "500")
    private HttpStatus errorCode;
    @Schema(example = "Server internal error")
    private String errorMessage;
    @Schema(example = "/api/products/2")
    private String apiPath;
    private LocalDateTime errorTime;


}
