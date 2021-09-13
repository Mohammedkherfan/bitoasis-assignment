package com.bitoasis.assignment.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Objects.isNull;

@Slf4j
@Component
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsSecurityService userDetailsSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = httpServletRequest.getHeader("Authorization");
        String email = getEmail(token);

        if (!isNull(email) && isNull(SecurityContextHolder.getContext().getAuthentication())) {
            UserDetails userDetails = userDetailsSecurityService.loadUserByUsername(email);
            if (!isNull(userDetails)) {
                if (tokenUtil.validateToken(token.substring(6), userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getEmail(String token) {
        String email = null;
        try {
            if (!isNull(token) && token.startsWith("Basic ")) {
                email = tokenUtil.getEmailFromToken(token.substring(6));
            }else {
                logger.warn("Invalid Token");
            }
            return email;
        }catch (Exception ex) {
            log.warn("Error during get email {}", ex);
            return email;
        }
    }
}
