package com.bv.platform.volunteers.domain.model.aggregates;

import com.bv.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import com.bv.platform.volunteers.domain.model.valueobjects.PersonalName;
import com.bv.platform.volunteers.domain.model.valueobjects.VolunteerPreferences;
import lombok.Getter;
import lombok.Setter;

@Getter
public class VolunteerProfile extends AbstractDomainAggregateRoot<VolunteerProfile> {

    @Setter
    private Long id;
    private Long userId;
    private PersonalName name;
    private String dniDocument;
    private String universityName;
    private String studentCode;
    private String phoneNumber;
    private int accumulatedHours;
    private VolunteerPreferences preferences;

    public VolunteerProfile() {
        this.preferences = VolunteerPreferences.empty();
        this.accumulatedHours = 0;
    }

    public VolunteerProfile(Long id, Long userId, PersonalName name, String dniDocument,
                            String universityName, String studentCode, String phoneNumber,
                            int accumulatedHours, VolunteerPreferences preferences) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.dniDocument = dniDocument;
        this.universityName = universityName;
        this.studentCode = studentCode;
        this.phoneNumber = phoneNumber;
        this.accumulatedHours = accumulatedHours;
        this.preferences = preferences != null ? preferences : VolunteerPreferences.empty();
    }

    public VolunteerProfile(Long userId, String firstName, String lastName, String dniDocument,
                            String universityName, String studentCode, String phoneNumber) {
        this(null, userId, new PersonalName(firstName, lastName), dniDocument,
                universityName, studentCode, phoneNumber, 0, VolunteerPreferences.empty());
    }

    public void updateProfile(String firstName, String lastName, String universityName,
                              String studentCode, String phoneNumber) {
        this.name = new PersonalName(firstName, lastName);
        this.universityName = universityName;
        this.studentCode = studentCode;
        this.phoneNumber = phoneNumber;
    }

    public void updatePreferences(VolunteerPreferences preferences) {
        this.preferences = preferences != null ? preferences : VolunteerPreferences.empty();
    }

    public void addAccumulatedHours(int hours) {
        if (hours > 0) {
            this.accumulatedHours += hours;
        }
    }
}
