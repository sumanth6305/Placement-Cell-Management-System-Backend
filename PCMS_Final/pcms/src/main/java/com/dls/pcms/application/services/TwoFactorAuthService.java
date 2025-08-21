package com.dls.pcms.application.services;

import com.dls.pcms.domain.models.TwoFactorAuth;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TwoFactorAuthService {

    TwoFactorAuth createTwoFactorAuth(TwoFactorAuth twoFactorAuth);

    Optional<TwoFactorAuth> getTwoFactorAuthById(UUID authId);

    List<TwoFactorAuth> getAllTwoFactorAuths();

    TwoFactorAuth updateTwoFactorAuth(UUID authId, TwoFactorAuth twoFactorAuthDetails);

    void deleteTwoFactorAuth(UUID authId);

    List<TwoFactorAuth> getTwoFactorAuthsByUserId(UUID userId);

    List<TwoFactorAuth> getTwoFactorAuthsByMethodType(String methodType);

    List<TwoFactorAuth> getTwoFactorAuthsByIsEnabled(boolean isEnabled);

    // Add more custom methods as needed
}
