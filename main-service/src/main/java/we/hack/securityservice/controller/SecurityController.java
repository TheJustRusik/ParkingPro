package we.hack.securityservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import we.hack.securityservice.model.dto.AuthRequest;
import we.hack.securityservice.model.dto.TokenResponse;
import we.hack.securityservice.model.dto.UserRequest;
import we.hack.securityservice.model.entity.User;
import we.hack.securityservice.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SecurityController {

    private final UserService userService;

    @PostMapping("/register")
    ResponseEntity<TokenResponse> register(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.register(userRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponse> authenticate(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(userService.authenticate(authRequest));
    }


    @PostMapping("/info")
    public ResponseEntity<User> getInfo(@RequestBody TokenResponse token) {
        return ResponseEntity.ok(userService.getInfo(token));
    }
}
