package com.takeaway.employee.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Hobby {
    private String id;
    private String description;
}
