package org.kenuki.parkingpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/protected")
public class AuthentificatedCon {
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "USER only endpoint")
    public ResponseEntity<String> hiUser() {
        return ResponseEntity.ok("You are USER!: ");
    }
    @GetMapping("/owner")
    @PreAuthorize("hasAuthority('OWNER')")
    @Operation(summary = "USER only endpoint")
    public ResponseEntity<String> hiOwner() {
        return ResponseEntity.ok("You are OWNER!: ");
    }
    @GetMapping("/any")
    @PreAuthorize("hasAnyAuthority('USER', 'OWNER')")
    @Operation(summary = "USER only endpoint")
    public ResponseEntity<String> hiUserOrOwner() {
        return ResponseEntity.ok("Hi!");
    }
}
