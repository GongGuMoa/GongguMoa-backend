package com.gonggumoa.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record PostProfileImageUrlRequest(
        @Schema(description = "업로드할 파일명", example = "profile.png")
        @NotBlank
        String filename,

        @Schema(description = "콘텐츠 타입", example = "image/jpeg")
        @NotBlank
        String contentType
) {
}
