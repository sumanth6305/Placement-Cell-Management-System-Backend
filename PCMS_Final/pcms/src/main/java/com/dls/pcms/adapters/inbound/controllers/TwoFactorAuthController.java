package com.dls.pcms.adapters.inbound.controllers;

import com.dls.pcms.domain.models.TwoFactorAuth;
import com.dls.pcms.application.services.TwoFactorAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/two-factor-auth")
@Slf4j
public class TwoFactorAuthController {

    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @PostMapping
    public ResponseEntity<TwoFactorAuth> createTwoFactorAuth(@Validated @RequestBody TwoFactorAuth twoFactorAuth) {
        log.info("Received request to create TwoFactorAuth with userId: {}", twoFactorAuth.getUserId());

        TwoFactorAuth createdTwoFactorAuth = twoFactorAuthService.createTwoFactorAuth(twoFactorAuth);

        if (createdTwoFactorAuth == null) {
            log.error("TwoFactorAuth creation failed. Returning bad request");
            return ResponseEntity.badRequest().build();
        }

        log.info("TwoFactorAuth created successfully with authId: {}", createdTwoFactorAuth.getAuthId());
        return new ResponseEntity<>(createdTwoFactorAuth, HttpStatus.CREATED);
    }

    @GetMapping("/{authId}")
    public ResponseEntity<TwoFactorAuth> getTwoFactorAuthById(@PathVariable UUID authId) {
        log.info("Received request to get TwoFactorAuth by ID: {}", authId);

        Optional<TwoFactorAuth> twoFactorAuth = twoFactorAuthService.getTwoFactorAuthById(authId);

        if (twoFactorAuth.isEmpty()) {
            log.info("No TwoFactorAuth found with ID: {}", authId);
            return ResponseEntity.notFound().build();
        }

        log.info("Returning TwoFactorAuth with ID: {}", authId);
        return ResponseEntity.ok(twoFactorAuth.get());
    }

    @GetMapping
    public ResponseEntity<List<TwoFactorAuth>> getAllTwoFactorAuths() {
        log.info("Received request to get all TwoFactorAuth records");

        List<TwoFactorAuth> twoFactorAuths = twoFactorAuthService.getAllTwoFactorAuths();

        if (twoFactorAuths.isEmpty()) {
            log.info("No TwoFactorAuth records found");
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} TwoFactorAuth records", twoFactorAuths.size());
        return ResponseEntity.ok(twoFactorAuths);
    }

    @PutMapping("/{authId}")
    public ResponseEntity<TwoFactorAuth> updateTwoFactorAuth(
            @PathVariable UUID authId,
            @Validated @RequestBody TwoFactorAuth twoFactorAuthDetails) {
        log.info("Received request to update TwoFactorAuth with ID: {}", authId);

        TwoFactorAuth updatedTwoFactorAuth = twoFactorAuthService.updateTwoFactorAuth(authId, twoFactorAuthDetails);

        if (updatedTwoFactorAuth == null) {
            log.info("TwoFactorAuth update failed. No record found with ID: {}", authId);
            return ResponseEntity.notFound().build();
        }

        log.info("TwoFactorAuth updated successfully with ID: {}", authId);
        return ResponseEntity.ok(updatedTwoFactorAuth);
    }

    @DeleteMapping("/{authId}")
    public ResponseEntity<Void> deleteTwoFactorAuth(@PathVariable UUID authId) {
        log.info("Received request to delete TwoFactorAuth with ID: {}", authId);

        twoFactorAuthService.deleteTwoFactorAuth(authId);

        log.info("TwoFactorAuth deleted successfully with ID: {}", authId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TwoFactorAuth>> getTwoFactorAuthsByUserId(@PathVariable UUID userId) {
        log.info("Received request to get TwoFactorAuth records by userId: {}", userId);

        List<TwoFactorAuth> twoFactorAuths = twoFactorAuthService.getTwoFactorAuthsByUserId(userId);

        if (twoFactorAuths.isEmpty()) {
            log.info("No TwoFactorAuth records found with userId: {}", userId);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} TwoFactorAuth records with userId: {}", twoFactorAuths.size(), userId);
        return ResponseEntity.ok(twoFactorAuths);
    }

    @GetMapping("/method/{methodType}")
    public ResponseEntity<List<TwoFactorAuth>> getTwoFactorAuthsByMethodType(@PathVariable String methodType) {
        log.info("Received request to get TwoFactorAuth records by methodType: {}", methodType);

        List<TwoFactorAuth> twoFactorAuths = twoFactorAuthService.getTwoFactorAuthsByMethodType(methodType);

        if (twoFactorAuths.isEmpty()) {
            log.info("No TwoFactorAuth records found with methodType: {}", methodType);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} TwoFactorAuth records with methodType: {}", twoFactorAuths.size(), methodType);
        return ResponseEntity.ok(twoFactorAuths);
    }

    @GetMapping("/enabled/{isEnabled}")
    public ResponseEntity<List<TwoFactorAuth>> getTwoFactorAuthsByIsEnabled(@PathVariable boolean isEnabled) {
        log.info("Received request to get TwoFactorAuth records with isEnabled: {}", isEnabled);

        List<TwoFactorAuth> twoFactorAuths = twoFactorAuthService.getTwoFactorAuthsByIsEnabled(isEnabled);

        if (twoFactorAuths.isEmpty()) {
            log.info("No TwoFactorAuth records found with isEnabled: {}", isEnabled);
            return ResponseEntity.noContent().build();
        }

        log.info("Returning {} TwoFactorAuth records with isEnabled: {}", twoFactorAuths.size(), isEnabled);
        return ResponseEntity.ok(twoFactorAuths);
    }
}
