package com.gonggumoa.domain.user.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record PostProfileImageUrlResponse(
        @Schema(description = "S3 Presigned URL (PUT 업로드용)")
        String uploadUrl,

        @Schema(description = "업로드된 파일의 S3 Key")
        String s3Key
) {
        public static PostProfileImageUrlResponse of(String uploadUrl, String s3Key) {
                return new PostProfileImageUrlResponse(uploadUrl, s3Key);
        }
}
