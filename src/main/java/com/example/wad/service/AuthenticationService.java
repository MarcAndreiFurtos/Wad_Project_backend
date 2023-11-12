package com.example.wad.service;

import com.example.wad.Entities.User;
import com.example.wad.dto.AuthenticationResponse;
import com.example.wad.dto.LoginRequest;
import com.example.wad.dto.RegisterRequest;
import com.example.wad.dto.UserDto;
import com.example.wad.exceptions.AuthenticationFailedException;
import com.example.wad.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.time.Instant;
import java.util.Optional;


@Service
@Transactional
@AllArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private HttpServletRequest request;


    public void signUp(RegisterRequest registerRequest){
         UserDto userDto = new UserDto();

         userDto.setUserId(userDto.createUserId());
         userDto.setUserName(registerRequest.getUsername());
         userDto.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
         userDto.setCreated(Instant.now());
         userDto.setBanned(false);
         userDto.setAdmin(false);

         User user = new User(userDto.getUserId(), userDto.getUserName(), userDto.getPassword(), userDto.getEmail(), userDto.getCreated(), userDto.isBanned(),userDto.isAdmin(),true);
         userRepository.save(user);
         //To implement email sending
    }
    public void adminSignUp(RegisterRequest registerRequest){
        UserDto userDto1 = new UserDto();

        userDto1.setUserId(userDto1.createUserId());
        userDto1.setUserName(registerRequest.getUsername());
        userDto1.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userDto1.setCreated(Instant.now());
        userDto1.setBanned(false);
        userDto1.setAdmin(true);

        User user = new User(userDto1.getUserId(), userDto1.getUserName(), userDto1.getPassword(), userDto1.getEmail(), userDto1.getCreated(), userDto1.isBanned(),userDto1.isAdmin(),true);
        userRepository.save(user);
        //To implement email sending
    }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        return userRepository.findByUserName("kkkk")
                .orElseThrow(() -> new UsernameNotFoundException("User name not found"));
    }



    public AuthenticationResponse logIn(LoginRequest loginRequest){

//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            return AuthenticationResponse.builder()
//                    .username(loginRequest.getUsername())
//                    .build();
//        } catch (AuthenticationException e) {
//            // Handle authentication failure (e.g., invalid credentials)
//            // Log the exception or return an appropriate response
//            return AuthenticationResponse.builder()
//                    .username("authentication_failed")
//                    .build();
//        }


        Optional<User> user = userRepository.findByUserName(loginRequest.getUsername());
        if (user.isPresent()) {
            String storedHashedPassword = user.get().getPassword();
            if ( passwordEncoder.matches(loginRequest.getPassword(), storedHashedPassword)){
                user.get().setLoggedIn(true);
                return AuthenticationResponse.builder()
                    .username(loginRequest.getUsername())
                    .build();
            }
        }

        return AuthenticationResponse.builder()
                .username("no")
                .build();


//            AuthenticationManager authenticate = (AuthenticationManager) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//            SecurityContextHolder.getContext().setAuthentication((Authentication) authenticate);
//
//            return AuthenticationResponse.builder()
//                    .username(loginRequest.getUsername())
//                    .build();
    }
    public boolean isLoggedIn() {
        return true;
    }
}


//import com.example.wad.Entities.User;
//import com.example.wad.Entities.VerificationToken;
//import com.example.wad.dto.*;
//import com.example.wad.exceptions.SpringRedditException;
//import com.example.wad.repository.UserRepository;
//import com.example.wad.repository.VerificationTokenRepository;
//import com.example.wad.security.JwtProvider;
//import lombok.AllArgsConstructor;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//@Transactional
//public class AuthenticationService {
//
//    private final PasswordEncoder passwordEncoder;
//    private final UserRepository userRepository;
//    private final VerificationTokenRepository verificationTokenRepository;
//
//    private final AuthenticationManager authenticationManager;
//    private final JwtProvider jwtProvider;
//
//    public void signup(RegisterRequest registerRequest) {
//         UserDto userDto = new UserDto();
//
//         userDto.setUserId(userDto.createUserId());
//         userDto.setUserName(registerRequest.getUsername());
//         userDto.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
//         userDto.setCreated(Instant.now());
//         userDto.setBanned(false);
//         userDto.setAdmin(false);
//
//         User user = new User(userDto.getUserId(), userDto.getUserName(), userDto.getPassword(), userDto.getEmail(), userDto.getCreated(), userDto.isBanned(),userDto.isAdmin());
//         userRepository.save(user);
//
//    }
//
//    @Transactional(readOnly = true)
//    public User getCurrentUser() {
//        Jwt principal = (Jwt) SecurityContextHolder.
//                getContext().getAuthentication().getPrincipal();
//        return userRepository.findByUserName(principal.getSubject())
//                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
//    }
//
////    private void fetchUserAndEnable(VerificationToken verificationToken) {
////        String username = verificationToken.getUser().getUserName();
////        User user = userRepository.findByUserName(username).orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
////        user.setEnabled(true);
////        userRepository.save(user);
////    }
//
//    private String generateVerificationToken(User user) {
//        String token = UUID.randomUUID().toString();
//        VerificationToken verificationToken = new VerificationToken();
//        verificationToken.setToken(token);
//        verificationToken.setUser(user);
//
//        verificationTokenRepository.save(verificationToken);
//        return token;
//    }
//
////    public void verifyAccount(String token) {
////        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
////        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token")));
////    }
//
//    public AuthenticationResponse login(LoginRequest loginRequest) {
//        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
//                loginRequest.getPassword()));
//        SecurityContextHolder.getContext().setAuthentication(authenticate);
//        String token = jwtProvider.generateToken(authenticate);
//        return AuthenticationResponse.builder()
//                .authenticationToken(token)
////                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
//                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
//                .username(loginRequest.getUsername())
//                .build();
//    }
//
////    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
////        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
////        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
////        return AuthenticationResponse.builder()
////                .authenticationToken(token)
////                .refreshToken(refreshTokenRequest.getRefreshToken())
////                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
////                .username(refreshTokenRequest.getUsername())
////                .build();
////    }
//
//    public boolean isLoggedIn() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
//    }
//}