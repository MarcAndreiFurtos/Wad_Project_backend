package com.example.wad.controllers;

import com.example.wad.dto.LoginRequest;
import com.example.wad.dto.RegisterRequest;
import com.example.wad.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.HttpStatus.*;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest){
        authenticationService.signUp(registerRequest);
        return new ResponseEntity<>("user registration successful", OK);
    }
    @PostMapping("/adminSignup")
    public ResponseEntity<String> adminSignup(@RequestBody RegisterRequest registerRequest){
        authenticationService.adminSignUp(registerRequest);
        return new ResponseEntity<>("admin registration successful",OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        authenticationService.logIn(loginRequest);
        return new ResponseEntity<>("login successfull", OK);
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        return new ResponseEntity<>("Logout successful",OK);
    }

}
