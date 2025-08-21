package com.dls.pcms.domain.repository;

import com.dls.pcms.domain.models.TwoFactorAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TwoFactorAuthRepository extends JpaRepository<TwoFactorAuth, UUID> {

    List<TwoFactorAuth> findByUserId(UUID userId);

    List<TwoFactorAuth> findByMethodType(String methodType);

    List<TwoFactorAuth> findByIsEnabled(boolean isEnabled);

    // Add custom methods as needed

    default List<TwoFactorAuth> findByUserIdAndMethodType(@Param("userId") UUID userId, @Param("methodType") String methodType) {
        // Custom logic to find two-factor auth entries by userId and methodType
        List<TwoFactorAuth> twoFactorAuthList = null;
        // Implement your custom logic here
        return twoFactorAuthList;
    }
}
