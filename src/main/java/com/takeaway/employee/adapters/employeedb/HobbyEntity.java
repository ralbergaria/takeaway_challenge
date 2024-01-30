package com.takeaway.employee.adapters.employeedb;

import com.takeaway.employee.domain.model.Hobby;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@Entity
@AllArgsConstructor
@Table(name = "hobbies")
class HobbyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;
    private String description;

    public HobbyEntity() {}

    static HobbyEntity fromDomain(Hobby hobby) {
        return HobbyEntity.builder().id(UUID.fromString(hobby.getId())).description(hobby.getDescription()).build();
    }

    Hobby toDomain() {
        return Hobby.builder().id(this.getId().toString()).description(this.getDescription()).build();
    }
}
