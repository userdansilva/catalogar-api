package com.catalogar.common.config;

import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utilities {
    private Utilities() {
        throw new IllegalStateException("Utility class. Don't instantiate");
    }

    public static Map<String, String> filterClaims(Jwt jwt) {
        final String[] claimKeys = {"sub", "aud", "ver", "iss", "name", "oid", "emails"};
        final List<String> includeClaims = Arrays.asList(claimKeys);

        Map<String,String> filteredClaims = new HashMap<>();
        Map<String, Object> claims = jwt.getClaims();

        includeClaims.forEach(claim -> {
            if (claims.containsKey(claim)) {
                filteredClaims.put(claim, claims.get(claim).toString());
            }
        });

        return filteredClaims;
    }
}
