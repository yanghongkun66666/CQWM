package com.kkWithCodex.cqwm.employee.service;

import com.kkWithCodex.cqwm.common.exception.BusinessException;
import com.kkWithCodex.cqwm.common.result.ErrorCode;
import com.kkWithCodex.cqwm.common.result.PageResult;
import com.kkWithCodex.cqwm.common.util.CurrentUserContext;
import com.kkWithCodex.cqwm.employee.dto.EmployeeCreateRequest;
import com.kkWithCodex.cqwm.employee.dto.EmployeeQueryRequest;
import com.kkWithCodex.cqwm.employee.dto.EmployeeStatusUpdateRequest;
import com.kkWithCodex.cqwm.employee.dto.EmployeeUpdateRequest;
import com.kkWithCodex.cqwm.employee.model.Employee;
import com.kkWithCodex.cqwm.employee.repository.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(EmployeeMapper employeeMapper, PasswordEncoder passwordEncoder) {
        this.employeeMapper = employeeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Employee create(EmployeeCreateRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setUsername(request.getUsername());
        employee.setPhone(request.getPhone());
        employee.setStatus(com.kkWithCodex.cqwm.employee.model.EmployeeStatus.ENABLED);
        employee.setGender(request.getGender());
        employee.setIdNumber(request.getIdNumber());
        employee.setLastOperateAt(now);
        employee.setCreatedBy(resolveCreator());
        employee.setCreatedAt(now);
        employee.setUpdatedAt(now);
        employee.setDeleted(false);
        String rawPwd = buildDefaultPassword(request.getIdNumber());
        employee.setPasswordHash(passwordEncoder.encode(rawPwd));
        employeeMapper.insert(employee);
        log.info("Employee created by {}: username={}, name={}", employee.getCreatedBy(), employee.getUsername(), employee.getName());
        return employee;
    }

    @Transactional
    public Employee update(Long id, EmployeeUpdateRequest request) {
        Employee existing = employeeMapper.findById(id);
        if (existing == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "员工不存在");
        }
        LocalDateTime now = LocalDateTime.now();
        existing.setName(request.getName());
        existing.setUsername(request.getUsername());
        existing.setPhone(request.getPhone());
        existing.setGender(request.getGender());
        existing.setIdNumber(request.getIdNumber());
        existing.setLastOperateAt(now);
        existing.setUpdatedAt(now);
        employeeMapper.update(existing);
        log.info("Employee updated by {}: id={}, username={}", resolveCreator(), existing.getId(), existing.getUsername());
        return existing;
    }

    @Transactional
    public void updateStatus(Long id, EmployeeStatusUpdateRequest request) {
        Employee existing = employeeMapper.findById(id);
        if (existing == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "员工不存在");
        }
        employeeMapper.updateStatus(id, request.getStatus(), LocalDateTime.now());
        log.info("Employee status updated by {}: id={}, newStatus={}", resolveCreator(), id, request.getStatus());
    }

    public PageResult<Employee> page(EmployeeQueryRequest request) {
        int page = Math.max(request.getPage(), 1);
        int size = Math.max(request.getPageSize(), 1);
        PageHelper.startPage(page, size);
        List<Employee> records = employeeMapper.findPageByName(request.getName());
        PageInfo<Employee> pageInfo = new PageInfo<>(records);
        return new PageResult<>(records, pageInfo.getTotal(), page, size);
    }

    private String buildDefaultPassword(String idNumber) {
        String suffix = (idNumber != null && idNumber.length() >= 6) ? idNumber.substring(idNumber.length() - 6) : "";
        return "123456" + suffix;
    }

    private String resolveCreator() {
        String user = CurrentUserContext.getUsername();
        return (user == null || user.isBlank()) ? "system" : user;
    }
}
