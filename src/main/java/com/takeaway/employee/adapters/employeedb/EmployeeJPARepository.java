package com.takeaway.employee.adapters.employeedb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeJPARepository extends JpaRepository<EmployeeEntity, UUID> {}
