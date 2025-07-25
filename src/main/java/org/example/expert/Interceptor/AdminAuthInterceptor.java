package org.example.expert.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.user.enums.UserRole;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //현재 시간을 정의된 포맷으로 변경
        String requestTime = LocalDateTime.now().format(DATE_TIME_FORMATTER);

        log.info("API 요청 시각 {}", requestTime);
        log.info("API 요청 URL {}", request.getRequestURI());

        Object role = request.getAttribute("userRole");
        if(role == null || !UserRole.ADMIN.name().equals(role.toString())) {
            log.warn("관리자 권한 없는 사용자 접근 시도 {} : URI", request.getRequestURI());
            throw new AccessDeniedException("관리자 권한이 없습니다");
        }

        log.info("관리자 권한 확인 {} : URI", request.getRequestURI());
        return true;
    }
}
