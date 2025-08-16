package com.gonggumoa.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record GetUserLocationResponse (
        @Schema(description = "주소 문자열", example = "서울특별시 중구 세종대로 110")
        String location,

        @Schema(description = "위도", example = "37.5351")
        Double latitude,

        @Schema(description = "경도", example = "127.0825")
        Double longitude
) {
    public static GetUserLocationResponse of(String location, Double latitude, Double longitude) {
        return new GetUserLocationResponse(location, latitude, longitude);
    }
}
