package we.hack.securityservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import we.hack.securityservice.exception.InvalidCredentialsException;
import we.hack.securityservice.exception.UserNotFoundException;
import we.hack.securityservice.model.dto.*;
import we.hack.securityservice.model.entity.User;
import we.hack.securityservice.repository.UserRepository;
import we.hack.securityservice.utils.JwtTokenUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.kafka.queues.email}")
    private String emailSendingQueue;

    public TokenResponse register(UserRequest userRequest) {

        User user = User.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);

        sendGreetingEmail(userRequest);
        return new TokenResponse(token);
    }

    public User getInfo(TokenResponse token) {
        String username = jwtTokenUtils.extractUsername(token.token());
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public TokenResponse authenticate(AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.username());
        String token = jwtTokenUtils.generateToken(userDetails);

        return new TokenResponse(token);
    }

    private void sendGreetingEmail(UserRequest userRequest) {
        kafkaTemplate.send(emailSendingQueue, new EmailMessageDto(
                userRequest.getEmail(), "Hello, " + userRequest.getUsername() + "! Welcome to our service!"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}