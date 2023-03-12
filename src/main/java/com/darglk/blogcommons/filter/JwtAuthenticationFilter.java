package com.darglk.blogcommons.filter;

import com.darglk.blogcommons.exception.CustomException;
import com.darglk.blogcommons.exception.ErrorResponse;
import com.darglk.blogcommons.exception.NotAuthorizedException;
import com.darglk.blogcommons.model.UserPrincipal;
import com.darglk.blogcommons.utils.JwtParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.logging.log4j.util.Strings;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.common.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.darglk.blogcommons.utils.JSONUtils.toJson;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final RealmResource realmResource;
    private final String jwtKey;
    private final AuthUserService authUserService;

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            RealmResource realmResource,
            AuthUserService authUserService,
            String jwtKey
    ) {
        super(authenticationManager);
        this.jwtKey = jwtKey;
        this.realmResource = realmResource;
        this.authUserService = authUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication;
        response.setContentType("application/json");
        try {
            authentication = getAuthentication(request);
        } catch (CustomException error) {
            response.setStatus(error.getStatusCode());
            response.getWriter().write(toJson(Map.of("errors", error.serializeErrors())));
            return;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.getWriter().write(toJson(Map.of("errors", List.of(new ErrorResponse("Something went wrong", null)))));
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            try {
                var parsedToken = JwtParser.parseToken(token, jwtKey);
                String userId = parsedToken.getBody().getSubject();
                var user = realmResource.users().get(userId);
                var sessionId = parsedToken.getBody().get("sid").toString();
                var session = user.getUserSessions()
                        .stream()
                        .filter(s -> s.getId().equals(sessionId))
                        .findAny();
                if (session.isEmpty()) {
                    throw new NotAuthorizedException();
                }
                var userResponse = authUserService.getUser(userId);
                if (!userResponse.getEnabled()) {
                    throw new NotAuthorizedException();
                }

                var authorities = userResponse.getAuthorities()
                        .stream()
                        .map(a -> new SimpleGrantedAuthority(a.getName()))
                        .collect(Collectors.toList());

                final var principal = new UserPrincipal(userId, userResponse.getEmail(), sessionId);
                return new UsernamePasswordAuthenticationToken(principal, null, authorities);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new NotAuthorizedException();
            }
        }
        throw new NotAuthorizedException();
    }
}
