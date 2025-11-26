package com.kkWithCodex.cqwm.employee.dto;

import com.kkWithCodex.cqwm.employee.model.EmployeeStatus;
import jakarta.validation.constraints.NotNull;

public class EmployeeStatusUpdateRequest {

    @NotNull(message = "状态必填")
    private EmployeeStatus status;

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }
}
