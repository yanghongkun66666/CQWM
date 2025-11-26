package com.kkWithCodex.cqwm.employee.repository;

import com.kkWithCodex.cqwm.employee.model.Employee;
import com.kkWithCodex.cqwm.employee.model.EmployeeStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    int insert(Employee employee);

    int update(Employee employee);

    int updateStatus(@Param("id") Long id, @Param("status") EmployeeStatus status, @Param("lastOperateAt") java.time.LocalDateTime lastOperateAt);

    Employee findById(@Param("id") Long id);

    Employee findByUsername(@Param("username") String username);

    List<Employee> findPageByName(@Param("name") String name);
}
