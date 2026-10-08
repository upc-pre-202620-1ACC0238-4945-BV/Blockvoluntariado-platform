package com.bv.platform.volunteers.application.internal.commandservices;

import com.bv.platform.shared.application.result.ApplicationError;
import com.bv.platform.shared.application.result.Result;
import com.bv.platform.volunteers.application.commandservices.VolunteerCommandService;
import com.bv.platform.volunteers.domain.model.aggregates.VolunteerProfile;
import com.bv.platform.volunteers.domain.model.commands.CreateVolunteerProfileCommand;
import com.bv.platform.volunteers.domain.model.commands.UpdateVolunteerPreferencesCommand;
import com.bv.platform.volunteers.domain.model.commands.UpdateVolunteerProfileCommand;
import com.bv.platform.volunteers.domain.model.valueobjects.VolunteerPreferences;
import com.bv.platform.volunteers.domain.repositories.VolunteerProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class VolunteerCommandServiceImpl implements VolunteerCommandService {

    private final VolunteerProfileRepository repository;

    public VolunteerCommandServiceImpl(VolunteerProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public Result<VolunteerProfile, ApplicationError> handle(CreateVolunteerProfileCommand command) {
        if (repository.existsByUserId(command.userId())) {
            return Result.failure(ApplicationError.conflict("VolunteerProfile", "A profile already exists for this user"));
        }
        if (repository.existsByDniDocument(command.dniDocument())) {
            return Result.failure(ApplicationError.conflict("VolunteerProfile", "DNI document is already registered"));
        }

        var profile = new VolunteerProfile(
                command.userId(),
                command.firstName(),
                command.lastName(),
                command.dniDocument(),
                command.universityName(),
                command.studentCode(),
                command.phoneNumber()
        );
        var saved = repository.save(profile);
        return Result.success(saved);
    }

    @Override
    public Result<VolunteerProfile, ApplicationError> handle(UpdateVolunteerProfileCommand command) {
        var existing = repository.findById(command.volunteerId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("VolunteerProfile", String.valueOf(command.volunteerId())));
        }
        var profile = existing.get();
        profile.updateProfile(
                command.firstName(),
                command.lastName(),
                command.universityName(),
                command.studentCode(),
                command.phoneNumber()
        );
        var updated = repository.save(profile);
        return Result.success(updated);
    }

    @Override
    public Result<VolunteerProfile, ApplicationError> handle(UpdateVolunteerPreferencesCommand command) {
        var existing = repository.findById(command.volunteerId());
        if (existing.isEmpty()) {
            return Result.failure(ApplicationError.notFound("VolunteerProfile", String.valueOf(command.volunteerId())));
        }
        var profile = existing.get();
        var preferences = new VolunteerPreferences(
                command.causes(),
                command.availability(),
                command.preferredModality()
        );
        profile.updatePreferences(preferences);
        var updated = repository.save(profile);
        return Result.success(updated);
    }

    @Override
    public void addAccumulatedHours(Long volunteerId, int hours) {
        repository.findById(volunteerId).ifPresent(profile -> {
            profile.addAccumulatedHours(hours);
            repository.save(profile);
        });
    }
}
