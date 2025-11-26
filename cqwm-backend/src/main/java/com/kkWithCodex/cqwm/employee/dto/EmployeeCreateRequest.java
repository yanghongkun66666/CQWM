package com.kkWithCodex.cqwm.employee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.kkWithCodex.cqwm.employee.model.Gender;

public class EmployeeCreateRequest {

    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名过长")
    private String name;

    @NotBlank(message = "账号不能为空")
    @Size(max = 50, message = "账号过长")
    private String username;

    @Pattern(regexp = "^\\d{11}$", message = "手机号需为11位数字")
    private String phone;

    @NotNull(message = "性别不能为空")
    private Gender gender;

    @Pattern(regexp = "^[0-9]{17}[0-9Xx]$", message = "身份证号需为18位，最后一位可为数字或X")
    private String idNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
}
