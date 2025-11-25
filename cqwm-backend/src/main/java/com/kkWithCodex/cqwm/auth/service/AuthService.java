package com.kkWithCodex.cqwm.auth.service;

import com.kkWithCodex.cqwm.auth.dto.AdminProfile;
import com.kkWithCodex.cqwm.auth.dto.LoginRequest;
import com.kkWithCodex.cqwm.auth.dto.LoginResponse;
import com.kkWithCodex.cqwm.auth.model.AdminAccountStatus;
import com.kkWithCodex.cqwm.auth.model.AdminUser;
import com.kkWithCodex.cqwm.auth.repository.AdminUserMapper;
import com.kkWithCodex.cqwm.common.exception.BusinessException;
import com.kkWithCodex.cqwm.common.result.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final AdminUserMapper adminUserMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(AdminUserMapper adminUserMapper,
                       PasswordEncoder passwordEncoder,
                       JwtTokenProvider jwtTokenProvider) {
        this.adminUserMapper = adminUserMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        AdminUser adminUser = adminUserMapper.findActiveByUsername(request.getUsername());

        if (adminUser == null) {
            throw new BusinessException(ErrorCode.AUTH_FAILED, "用户名或密码错误");
        }

        if (AdminAccountStatus.DISABLED.equals(adminUser.getStatus())) {
            throw new BusinessException(ErrorCode.ACCOUNT_DISABLED, "账户已被禁用，请联系管理员");
        }

        if (!passwordEncoder.matches(request.getPassword(), adminUser.getPasswordHash())) {
            throw new BusinessException(ErrorCode.AUTH_FAILED, "用户名或密码错误");
        }

        LocalDateTime now = LocalDateTime.now();
        adminUser.setLastLoginAt(now);
        adminUser.setLastLoginIp(request.getLoginIp());
        adminUser.setUpdatedAt(now);
        adminUserMapper.updateLoginAudit(adminUser);

        boolean rememberMe = request.isRememberMe();
        String token = jwtTokenProvider.generateToken(adminUser.getId(), adminUser.getUsername(), rememberMe);
        long expiresIn = jwtTokenProvider.getExpiration(rememberMe);
        return new LoginResponse(token, expiresIn, toProfile(adminUser));
    }

    private AdminProfile toProfile(AdminUser adminUser) {
        return new AdminProfile(
                adminUser.getId(),
                adminUser.getUsername(),
                adminUser.getFullName(),
                adminUser.getPhone(),
                adminUser.getEmail()
        );
    }
}
