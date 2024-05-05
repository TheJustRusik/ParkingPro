package org.kenuki.parkingpro.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.kenuki.parkingpro.dtos.LoginDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/open")
public class Test {
    @GetMapping("/")
    @Operation(summary = "Test endpoint")
    public ResponseEntity<String> loginUser() {
        return ResponseEntity.ok("Hi!");
    }
}
