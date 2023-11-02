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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.security.Principal;
import java.time.Instant;


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
         userDto.setEmail(registerRequest.getEmail());
         userDto.setCreated(Instant.now());
         userDto.setBanned(false);
         userDto.setAdmin(false);

         User user = new User(userDto.getUserId(), userDto.getUserName(), userDto.getPassword(), userDto.getEmail(), userDto.getCreated(), userDto.isBanned(),userDto.isAdmin());
         userRepository.save(user);
         //To implement email sending
    }
    public void adminSignUp(RegisterRequest registerRequest){
        UserDto userDto1 = new UserDto();

        userDto1.setUserId(userDto1.createUserId());
        userDto1.setUserName(registerRequest.getUsername());
        userDto1.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userDto1.setEmail(registerRequest.getEmail());
        userDto1.setCreated(Instant.now());
        userDto1.setBanned(false);
        userDto1.setAdmin(true);

        User user = new User(userDto1.getUserId(), userDto1.getUserName(), userDto1.getPassword(), userDto1.getEmail(), userDto1.getCreated(), userDto1.isBanned(),userDto1.isAdmin());
        userRepository.save(user);
        //To implement email sending
    }
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + userName));
    }


    public AuthenticationResponse logIn(LoginRequest loginRequest){
        try {
            AuthenticationManager authenticate = (AuthenticationManager) authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication((Authentication) authenticate);

            return AuthenticationResponse.builder()
//                    .authenticationToken(token)
//                    .refreshToken(refreshTokenService.generateRefreshToken().getToken())
//                    .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                    .username(loginRequest.getUsername())
                    .build();
        }
        catch(Exception authenticationFailedException){
            throw new AuthenticationFailedException("Authentication failed: " + authenticationFailedException.getMessage(), authenticationFailedException);
        }
    }
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}

