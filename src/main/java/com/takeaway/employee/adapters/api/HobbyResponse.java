package com.takeaway.employee.adapters.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.takeaway.employee.domain.model.Hobby;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
class HobbyResponse {
    private String id;
    private String description;

    static HobbyResponse fromDomain(Hobby hobby) {
        return HobbyResponse.builder().id(hobby.getId()).description(hobby.getDescription()).build();
    }
}
