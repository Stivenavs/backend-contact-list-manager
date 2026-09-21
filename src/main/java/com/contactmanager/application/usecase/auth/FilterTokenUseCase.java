package com.contactmanager.application.usecase.auth;

import com.contactmanager.application.exceptions.ApiException;
import com.contactmanager.application.exceptions.ExpiredTokenException;
import com.contactmanager.application.exceptions.InvalidTokenException;
import com.contactmanager.application.exceptions.enums.ContactManagerErrorCodes;
import com.contactmanager.infrastructure.ports.in.TokenUseCase;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FilterTokenUseCase extends OncePerRequestFilter {

    private final TokenUseCase tokenUseCase;
    private final MessageSource messageSource;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        return path.contains("/swagger-ui")
                || path.contains("/v3/api-docs")
                || path.contains("/swagger-resources")
                || path.contains("/webjars")
                || path.contains("/auth/recover")
                || path.contains("/auth/login")
                || path.contains("/auth/login-document");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {

        addCorsHeaders(response);

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        try {
            validByEndpoint(request);
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException ex) {
            handleApiException(response, ex);
        }
    }


    private void validByEndpoint(HttpServletRequest request) {

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/v1/contactmanager/auth/reset-password")) {
            validToken(request);
        }
//        else if (requestURI.startsWith("/api/v1/contactmanager/user")) {
//            Jws<Claims> jws = validToken(request);
//            Object rol = jws.getPayload().get("rol");
//            String rolAdmin = "ADMIN";
//
//            if (!rolAdmin.equals(String.valueOf(rol).toUpperCase())) {
//                throw new InvalidTokenException( ContactManagerErrorCodes.INVALID_PERMISSIONS, "");
//            }
//        }
    }

    private Jws<Claims> validToken(HttpServletRequest request) {
        String token = getToken(request);

        if (token == null) {
            throw new InvalidTokenException(ContactManagerErrorCodes.TOKEN_NOT_FOUND, "");
        }

        try {
            return tokenUseCase.parseJwt(token);
        } catch (ExpiredJwtException ex) {
            throw new ExpiredTokenException(ContactManagerErrorCodes.EXPIRED_TOKEN, ex.getMessage());
        } catch (Exception ex) {
            throw new InvalidTokenException(ContactManagerErrorCodes.INVALID_TOKEN, ex.getMessage());
        }
    }


    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private void addCorsHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");
    }

    private void handleApiException(HttpServletResponse response, ApiException ex) {

        response.setStatus(resolveHttpStatus(ex));
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String errorCode = ex.getCode().getPrefix() + "-" + ex.getCode().getCode();

        String message = messageSource.getMessage(
                ex.getCode().getLocalizedMessage(),
                null,
                LocaleContextHolder.getLocale()
        );

        String json = """
        {
          "code": "%s",
          "message": "%s"
        }
        """.formatted(errorCode, message);

        writeJson(response, json);
    }

    private int resolveHttpStatus(ApiException ex) {
        return switch ((ContactManagerErrorCodes) ex.getCode()) {
            case INVALID_TOKEN, EXPIRED_TOKEN -> HttpServletResponse.SC_UNAUTHORIZED; // 401
            case INVALID_PERMISSIONS -> HttpServletResponse.SC_FORBIDDEN; // 403
            case USER_NOT_FOUND -> HttpServletResponse.SC_NOT_FOUND; // 404
            default -> HttpServletResponse.SC_BAD_REQUEST; // 400
        };
    }

    private void writeJson(HttpServletResponse response, String json) {
        try {
            response.getWriter().write(json);
            response.getWriter().flush();
        } catch (IOException ignored) {}
    }
}

