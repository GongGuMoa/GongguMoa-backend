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
        이 API는 시스템에 정의된 모든 에러 코드를 나타냅니다.
        """)
    @GetMapping(value = "/error-codes", produces = "text/html;charset=UTF-8")
    public ResponseEntity<String> getErrorCodeHtmlTable() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table border='1' cellpadding='8' cellspacing='0'>");
        sb.append("<tr><th>Code</th><th>Message</th></tr>");

        for (BaseExceptionResponseStatus status : BaseExceptionResponseStatus.values()) {
            sb.append(String.format("<tr><td>%d</td><td>%s</td></tr>", status.getCode(), status.getMessage()));
        }

        sb.append("</table>");
        return ResponseEntity.ok(sb.toString());
    }

}
