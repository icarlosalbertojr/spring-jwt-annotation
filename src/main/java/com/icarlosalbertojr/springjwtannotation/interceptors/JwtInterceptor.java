package com.icarlosalbertojr.springjwtannotation.interceptors;

import com.icarlosalbertojr.springjwtannotation.annotations.UncheckJwt;
import com.icarlosalbertojr.springjwtannotation.services.AuthService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!isCheckJWT(handler)) {
            return true;
        }
        return checkJWT(request);
    }

    private boolean isCheckJWT(Object handler) {
        return handler instanceof HandlerMethod
                && !((HandlerMethod) handler).getMethod().isAnnotationPresent(UncheckJwt.class);
    }

    private boolean checkJWT(HttpServletRequest request) {
        String tokenWithBearer = request.getHeader("Authorization");

        if (Objects.isNull(tokenWithBearer) || !tokenWithBearer.contains("Bearer")) {
            throw new IllegalArgumentException("Token invalid!");
        }

        String token = tokenWithBearer.replace("Bearer", "").trim();

        if (Objects.isNull(token) || token.isEmpty()) {
            throw new IllegalArgumentException("Token invalid!");
        }

        authService.validateToken(token);

        return true;
    }



}
