package com.platform.dentify.iam.domain.services;

public interface JwtService {
    String generateAccessToken(String userId);
    String generateRefreshToken(String userId);
    boolean isTokenValid(String token);
    String extractUserId(String token);

}
