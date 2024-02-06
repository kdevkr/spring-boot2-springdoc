package com.example.demo.error;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Schema(accessMode = Schema.AccessMode.READ_ONLY)
@Accessors(chain = true)
@Getter
@Setter
public class ErrorResponse {
    @Schema(example = "Internal Server Error")
    private String message;
    @Schema(example = "999")
    private String errorCode;
    @Schema(example = "an unexpected error")
    private String hint;
    private Instant timestamp;
}
