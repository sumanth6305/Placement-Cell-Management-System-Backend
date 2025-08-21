package com.dls.pcms.infrastructure.repositories;

import com.dls.pcms.application.services.TwoFactorAuthService;
import com.dls.pcms.domain.models.TwoFactorAuth;
import com.dls.pcms.domain.repository.TwoFactorAuthRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class TwoFactorAuthServiceImpl implements TwoFactorAuthService {

    @Autowired
    private TwoFactorAuthRepository twoFactorAuthRepository;

    @Override
    public TwoFactorAuth createTwoFactorAuth(TwoFactorAuth twoFactorAuth) {
        log.info("Creating new TwoFactorAuth with userId: {}", twoFactorAuth.getUserId());
        TwoFactorAuth savedTwoFactorAuth = twoFactorAuthRepository.save(twoFactorAuth);
        log.info("TwoFactorAuth created successfully with authId: {}", savedTwoFactorAuth.getAuthId());
        return savedTwoFactorAuth;
    }

    @Override
    public Optional<TwoFactorAuth> getTwoFactorAuthById(UUID authId) {
        log.info("Fetching TwoFactorAuth by ID: {}", authId);
        Optional<TwoFactorAuth> twoFactorAuth = twoFactorAuthRepository.findById(authId);
        if (twoFactorAuth.isPresent()) {
            log.info("TwoFactorAuth found with ID: {}", authId);
        } else {
            log.info("No TwoFactorAuth found with ID: {}", authId);
        }
        return twoFactorAuth;
    }

    @Override
    public List<TwoFactorAuth> getAllTwoFactorAuths() {
        log.info("Fetching all TwoFactorAuth records");
        List<TwoFactorAuth> twoFactorAuths = twoFactorAuthRepository.findAll();
        log.info("Found {} TwoFactorAuth records", twoFactorAuths.size());
        return twoFactorAuths;
    }

    @Override
    public TwoFactorAuth updateTwoFactorAuth(UUID authId, TwoFactorAuth twoFactorAuthDetails) {
        log.info("Updating TwoFactorAuth with ID: {}", authId);
        Optional<TwoFactorAuth> existingTwoFactorAuthOpt = twoFactorAuthRepository.findById(authId);

        if (existingTwoFactorAuthOpt.isPresent()) {
            TwoFactorAuth existingTwoFactorAuth = existingTwoFactorAuthOpt.get();
            existingTwoFactorAuth.setUserId(twoFactorAuthDetails.getUserId());
            existingTwoFactorAuth.setMethodType(twoFactorAuthDetails.getMethodType());

            //existingTwoFactorAuth.setIsEnabled(twoFactorAuthDetails.isEnabled());
            existingTwoFactorAuth.setEnabled(twoFactorAuthDetails.isEnabled());

            existingTwoFactorAuth.setUpdatedAt(twoFactorAuthDetails.getUpdatedAt());

            TwoFactorAuth updatedTwoFactorAuth = twoFactorAuthRepository.save(existingTwoFactorAuth);
            log.info("TwoFactorAuth updated successfully with ID: {}", authId);
            return updatedTwoFactorAuth;
        } else {
            log.error("No TwoFactorAuth found with ID: {}", authId);
            return null;
        }
    }

    @Override
    public void deleteTwoFactorAuth(UUID authId) {
        log.info("Deleting TwoFactorAuth with ID: {}", authId);
        twoFactorAuthRepository.deleteById(authId);
        log.info("TwoFactorAuth deleted successfully with ID: {}", authId);
    }

    @Override
    public List<TwoFactorAuth> getTwoFactorAuthsByUserId(UUID userId) {
        log.info("Fetching TwoFactorAuth records with userId: {}", userId);
        List<TwoFactorAuth> twoFactorAuths = twoFactorAuthRepository.findByUserId(userId);
        log.info("Found {} TwoFactorAuth records with userId: {}", twoFactorAuths.size(), userId);
        return twoFactorAuths;
    }

    @Override
    public List<TwoFactorAuth> getTwoFactorAuthsByMethodType(String methodType) {
        log.info("Fetching TwoFactorAuth records with methodType: {}", methodType);
        List<TwoFactorAuth> twoFactorAuths = twoFactorAuthRepository.findByMethodType(methodType);
        log.info("Found {} TwoFactorAuth records with methodType: {}", twoFactorAuths.size(), methodType);
        return twoFactorAuths;
    }

    @Override
    public List<TwoFactorAuth> getTwoFactorAuthsByIsEnabled(boolean isEnabled) {
        log.info("Fetching TwoFactorAuth records with isEnabled: {}", isEnabled);
        List<TwoFactorAuth> twoFactorAuths = twoFactorAuthRepository.findByIsEnabled(isEnabled);
        log.info("Found {} TwoFactorAuth records with isEnabled: {}", twoFactorAuths.size(), isEnabled);
        return twoFactorAuths;
    }
}
