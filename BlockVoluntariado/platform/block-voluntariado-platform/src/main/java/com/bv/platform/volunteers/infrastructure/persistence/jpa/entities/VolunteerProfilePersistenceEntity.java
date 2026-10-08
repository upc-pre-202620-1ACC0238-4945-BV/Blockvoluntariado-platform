package com.bv.platform.volunteers.infrastructure.persistence.jpa.entities;

import com.bv.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "volunteer_profiles")
@Getter
@Setter
@NoArgsConstructor
public class VolunteerProfilePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "dni_document", nullable = false, unique = true, length = 15)
    private String dniDocument;

    @Column(name = "university_name", nullable = false, length = 150)
    private String universityName;

    @Column(name = "student_code", nullable = false, length = 20)
    private String studentCode;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "accumulated_hours", nullable = false)
    private int accumulatedHours = 0;

    @Column(name = "causes", length = 255)
    private String causes;

    @Column(name = "availability", length = 100)
    private String availability;

    @Column(name = "preferred_modality", length = 50)
    private String preferredModality;
}
