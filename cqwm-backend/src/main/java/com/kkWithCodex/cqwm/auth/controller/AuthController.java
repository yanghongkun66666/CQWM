package com.kkWithCodex.cqwm.auth.controller;

import com.kkWithCodex.cqwm.auth.dto.LoginRequest;
import com.kkWithCodex.cqwm.auth.dto.LoginResponse;
import com.kkWithCodex.cqwm.auth.service.AuthService;
import com.kkWithCodex.cqwm.common.result.ApiResponse;
import com.kkWithCodex.cqwm.common.util.IpUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
//（admin/Admin@123）  默认账号密码
    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request,
                                            HttpServletRequest httpServletRequest) {
        request.setLoginIp(IpUtils.resolveClientIp(httpServletRequest));
        return ApiResponse.success(authService.login(request));
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        // Stateless JWT: client clears its token; endpoint exists for symmetry/audit hooks.
        return ApiResponse.success(null);
    }
}
