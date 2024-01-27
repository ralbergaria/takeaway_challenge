package com.takeaway.employee.adapters.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
class EmployeeEndpoint {

    @GetMapping
    @CrossOrigin
    @Operation(summary = "Get a list of all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    List<EmployeeResponse> getAll() {
        return null;
    }

    @GetMapping("/{employeeId}")
    @CrossOrigin
    @Operation(summary = "Search for an employee by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    EmployeeResponse get(@PathVariable("employeeId") final String employeeId) {
        return null;
    }

    @PostMapping
    @CrossOrigin
    @Operation(summary = "Create an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    EmployeeResponse create(@RequestBody @Validated EmployeeRequest employeeRequest) {
        return null;
    }

    @PutMapping
    @CrossOrigin
    @Operation(summary = "Update an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    EmployeeResponse update(@RequestBody @Validated EmployeeRequest employeeRequest) {
        return null;
    }

    @DeleteMapping("/{employeeId}")
    @CrossOrigin
    @Operation(summary = "Delete an employee by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "500", description = "Internal Error")
    })
    EmployeeResponse delete(@PathVariable("employeeId") final String employeeId) {
        return null;
    }
}
