package com.oocl.parkingsmart.filter;

import io.jsonwebtoken.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private static String TOKEN_PREFIX = "Bearer ";
    private static String HEADER_STRING = "Authorization";

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        try {
            String header = req.getHeader(HEADER_STRING);

            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                chain.doFilter(req, res);
            }else {
                UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                chain.doFilter(req, res);
            }
        } catch (final MalformedJwtException | UnsupportedJwtException | SignatureException | ExpiredJwtException | IllegalArgumentException e) {
            // TODO: Exercise all exceptions to ensure none leak key material to logs
            final String errorMessage = "Unable to validate the access token.";
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.getWriter().write(errorMessage);
        }

    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(HEADER_STRING);

        String user = Jwts.parser()
                .setSigningKey("ParkingSmart")
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        List<String> roles = Jwts.parser()
                .setSigningKey("ParkingSmart")
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .get("roles", List.class);

        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, roles .stream() .map(SimpleGrantedAuthority::new) .collect(Collectors.toList()));
        }

        return null;
    }
}
