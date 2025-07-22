package com.gonggumoa.global.docs;

import com.gonggumoa.global.response.status.BaseExceptionResponseStatus;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/docs")
public class DocsController {

    @Operation(summary = "전체 에러 코드 목록", description = """
        ### ✅ 에러 코드 테이블
        이 API는 시스템에 정의된 모든 에러 코드를 Markdown 형식으로 반환합니다.
        """)
    @GetMapping("/error-codes")
    public ResponseEntity<String> getErrorCodeTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("| Code |           Message           |\n");
        sb.append("|------|-----------------------------|\n");

        for (BaseExceptionResponseStatus status : BaseExceptionResponseStatus.values()) {
            sb.append(String.format("| %d | %s |\n", status.getCode(), status.getMessage()));
        }

        return ResponseEntity.ok(sb.toString());
    }
}
