package com.takeaway.employee.adapters.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
class EmployeeEndpoint {
    private final EmployeeFacade employeeFacade;
    @GetMapping
    @CrossOrigin
    @Operation(summary = "Get a list of all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    List<EmployeeResponse> getAll() {
        return employeeFacade.getAll();
    }

    @GetMapping("/{employeeId}")
    @CrossOrigin
    @Operation(summary = "Search for an employee by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    EmployeeResponse get(@PathVariable("employeeId") final String employeeId) {
        return employeeFacade.getById(employeeId);
    }

    @PostMapping
    @CrossOrigin
    @Operation(summary = "Create an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    EmployeeResponse create(@RequestBody @Validated EmployeeCreateRequest employeeCreateRequest) {
        return employeeFacade.create(employeeCreateRequest);
    }

    @PutMapping
    @CrossOrigin
    @Operation(summary = "Update an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    EmployeeResponse update(@RequestBody @Validated EmployeeUpdateRequest employeeUpdateRequest) {
        return employeeFacade.update(employeeUpdateRequest);
    }

    @DeleteMapping("/{employeeId}")
    @CrossOrigin
    @Operation(summary = "Delete an employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    void delete(@PathVariable("employeeId") final String employeeId) {
        employeeFacade.delete(employeeId);
    }
}
