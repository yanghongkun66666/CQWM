package com.kkWithCodex.cqwm.employee.controller;

import com.kkWithCodex.cqwm.common.result.ApiResponse;
import com.kkWithCodex.cqwm.common.result.PageResult;
import com.kkWithCodex.cqwm.employee.dto.EmployeeCreateRequest;
import com.kkWithCodex.cqwm.employee.dto.EmployeeQueryRequest;
import com.kkWithCodex.cqwm.employee.dto.EmployeeStatusUpdateRequest;
import com.kkWithCodex.cqwm.employee.dto.EmployeeUpdateRequest;
import com.kkWithCodex.cqwm.employee.model.Employee;
import com.kkWithCodex.cqwm.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ApiResponse<PageResult<Employee>> page(@Valid EmployeeQueryRequest request) {
        return ApiResponse.success(employeeService.page(request));
    }

    @PostMapping
    public ApiResponse<Employee> create(@Valid @RequestBody EmployeeCreateRequest request) {
        return ApiResponse.success(employeeService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Employee> update(@PathVariable Long id, @Valid @RequestBody EmployeeUpdateRequest request) {
        return ApiResponse.success(employeeService.update(id, request));
    }

    @PatchMapping("/{id}/status")
    public ApiResponse<Void> updateStatus(@PathVariable Long id, @Valid @RequestBody EmployeeStatusUpdateRequest request) {
        employeeService.updateStatus(id, request);
        return ApiResponse.success(null);
    }
}
