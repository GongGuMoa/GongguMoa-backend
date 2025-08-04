package com.gonggumoa.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PostUserSetLocationRequest(
        @Schema(description = "위도", example = "37.5665")
        @NotNull
        Double latitude,

        @Schema(description = "경도", example = "126.9780")
        @NotNull
        Double longitude,

        @Schema(description = "주소 문자열", example = "서울특별시 중구 세종대로 110")
        @NotBlank
        String location
) {
}
