package com.redi.demo.services;

import com.redi.demo.repository.model.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static java.lang.String.format;

@Service
public class PermissionEvaluatorService implements PermissionEvaluator {
    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        final User user = (User) authentication.getPrincipal();
        final String effectivePermission = effectivePermission(targetType, permission);
        if (effectivePermission.equals("users.read")
                && user.getAuthorities().contains(new SimpleGrantedAuthority(effectivePermission))
                && targetId.toString().equals(user.getEmail())) {
            return true;
        }
        return false;
    }

    private static String effectivePermission(String targetType, Object permission) {
        return format("%s.%s", targetType, permission);
    }
}