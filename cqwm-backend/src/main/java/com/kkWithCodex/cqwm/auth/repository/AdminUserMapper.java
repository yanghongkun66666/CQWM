package com.kkWithCodex.cqwm.auth.repository;

import com.kkWithCodex.cqwm.auth.model.AdminUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminUserMapper {

    AdminUser findActiveByUsername(@Param("username") String username);

    int updateLoginAudit(AdminUser adminUser);
}
