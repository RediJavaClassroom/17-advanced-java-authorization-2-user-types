package com.redi.demo.controller;

import com.redi.demo.model.UserCredentials;
import com.redi.demo.model.UserRegistration;
import com.redi.demo.repository.model.User;
import com.redi.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserController(final UserService userService, final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @GetMapping(path = "/users/{email}")
    @ResponseBody
    @PreAuthorize("hasRole('ADMIN') || hasPermission(#email,'users','read')")
    public User get(@PathVariable final String email ) {
        return userService.getUser(email);
    }

    @PostMapping(path = "/users")
    @ResponseBody
    public User createUser(@RequestBody final UserRegistration userRegistration) {
        return userService.createUser(userRegistration);
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public User login(@RequestBody UserCredentials credentials) {
        System.out.println("Authenticating " + credentials);
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                credentials.email, credentials.password));
        System.out.println("authentication " + authentication);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("Getting user " + credentials);

        return userService.getUser(credentials.email);
    }
}
