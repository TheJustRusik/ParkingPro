package org.kenuki.parkingpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.kenuki.parkingpro.dtos.LoginDTO;
import org.kenuki.parkingpro.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private AuthService authService;
    @PostMapping("/login")
    @Operation(summary = "Login endpoint", description = "If username and password correct, it will send JWT-token, with that token user have access to ALL endpoints")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

}
