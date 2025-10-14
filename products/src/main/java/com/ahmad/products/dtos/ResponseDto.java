package com.ahmad.products.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@Schema(
        name = "Response",
        description = "Successful response"
)
public class ResponseDto {
    private String statusCode;
    private String message;
}
