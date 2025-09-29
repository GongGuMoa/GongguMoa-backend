package com.gonggumoa.global.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final S3Presigner s3Presigner;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    public Map<String, String> generatePresignedUrl(String filename, String contentType, Long userId) {
        // S3 Key 규칙: profile/user-{userId}/{UUID}-{filename}
        String key = "profile/user-" + userId + "/" + UUID.randomUUID() + "-" + filename;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(contentType)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(builder -> builder
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMinutes(5))
        );

        return Map.of(
                "uploadUrl", presignedRequest.url().toString(), // 프론트에서 PUT 요청할 URL
                "s3Key", key                                   // 서버 DB에 저장할 파일 경로
        );
    }
}
