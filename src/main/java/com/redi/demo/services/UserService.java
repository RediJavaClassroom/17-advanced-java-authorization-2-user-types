package com.redi.demo.services;

import com.redi.demo.model.UserRegistration;
import com.redi.demo.model.UserType;
import com.redi.demo.repository.UserRepository;
import com.redi.demo.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Set<String> ALL_PERMISSIONS = Set.of(
            "users.create",
            "users.delete",
            "users.read",
            "users.all.read",
            "short-links.delete",
            "short-links.create",
            "short-links.read",
            "short-links.all.read"
    );

    private static final Map<String, Collection<String>> ROLES = Map.ofEntries(
            Map.entry("ROLE_ADMIN", ALL_PERMISSIONS),
            Map.entry("ROLE_USER", List.of(
                    "short-links.create",
                    "short-links.read"
            )));

    private static final Map<String, Collection<String>> USER_ROLES =
            Map.ofEntries(
                    Map.entry("redi@redi.com", List.of("ROLE_ADMIN")),
                    Map.entry("varshatj@redi.com", List.of("ROLE_ADMIN"))
            );

    private static final String DEFAULT_ROLE = "ROLE_USER";
    public static final String ROLE_BASIC_USER = "ROLE_BASIC_USER";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<UserType, String> userTypeToRoleMap;

    @Autowired
    public UserService(final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userTypeToRoleMap = new HashMap<>();

        this.userTypeToRoleMap.put(UserType.BASIC, ROLE_BASIC_USER);
        this.userTypeToRoleMap.put(UserType.FREE, "ROLE_FREE_USER");
        this.userTypeToRoleMap.put(UserType.PREMIUM, "ROLE_PREMIUM_USER");
    }

    public User createUser(final UserRegistration userRegistration) {
        final User user = new User(userRegistration.email,
                userRegistration.name,
                passwordEncoder.encode(userRegistration.password),
                findAuthorities(
                        this.userTypeToRoleMap
                                .getOrDefault(
                                        userRegistration.userType,
                                        "ROLE_FREE_USER"
                                ),
                        userRegistration.email
                ),
                userRegistration.userType.name()
        );
        userRepository.save(user);
        return user;
    }

    public User getUser(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Return all the authorities (permissions) allowed for the user identified by the provided email
     */
    private static Collection<String> findAuthorities(
            String userTypeRole,
            String email
    ) {
        // Get all the roles for this user
        // {ROLE_A, ROLE_B}
        final Collection<String> roles = USER_ROLES.getOrDefault(email, List.of(DEFAULT_ROLE));

        // Get all the permissions for the user with roles
        // {a_1, a_2, b_1, b_2} because ROLE_A -> {a_1, 1_2} and ROLE_B -> {b_1, b_2}
        final Set<String> permissions = roles.stream()
                .map(ROLES::get)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        // Add all roles and permissions together and return as authorities
        final Collection<String> authorities = new HashSet<>();

        authorities.addAll(roles);
        authorities.addAll(permissions);
        authorities.add(userTypeRole);

        return authorities;
    }
}
