package com.gbsw.gbswhub.domain.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gbsw.gbswhub.domain.jwt.db.TokenErrorResponse;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class TokenExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        try{
            chain.doFilter(request, response);
        } catch (JwtException ex) {
            setErrorResponse(response, ex);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/actuator/health");
    }


    public void setErrorResponse(HttpServletResponse res, Throwable ex) throws IOException {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType("application/json; charset=utf-8");

        TokenErrorResponse jwtExeptionResponse = new TokenErrorResponse(ex.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        res.getWriter().write(mapper.writeValueAsString(jwtExeptionResponse));
    }
}
