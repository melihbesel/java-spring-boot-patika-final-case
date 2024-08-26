package com.patika.gatewayservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public Claims getAllClaimsFromToken(String token) {
        // Remove the "Bearer " prefix if it exists
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    private boolean isAuthority(String token, String roleType) {
        Map<String, Object> claims = this.getAllClaimsFromToken(token);
        Object rolesObject = claims.get("roles");
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Map<String, Object>> rolesList = mapper.convertValue(rolesObject, List.class);
            Set<String> roleNames = new HashSet<>();
            for (Map<String, Object> roleMap : rolesList) {
                roleNames.add((String) roleMap.get("roleName"));
            }
            return roleNames.contains(roleType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isInvalid(String token, String roleType) {
        return (!this.isAuthority(token, roleType) || this.isTokenExpired(token));
    }

}
