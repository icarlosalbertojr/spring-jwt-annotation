package com.icarlosalbertojr.springjwtannotation.interceptors;

import com.icarlosalbertojr.springjwtannotation.annotations.UncheckJwt;
import com.icarlosalbertojr.springjwtannotation.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return !isCheckJWT(handler) ? true : checkJWT(request);
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
