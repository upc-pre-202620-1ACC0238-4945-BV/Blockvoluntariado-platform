package com.bv.platform.iam.infrastructure.persistence.jpa.adapters;

import com.bv.platform.iam.domain.model.aggregates.User;
import com.bv.platform.iam.domain.repositories.UserRepository;
import com.bv.platform.iam.infrastructure.persistence.jpa.assemblers.UserPersistenceAssembler;
import com.bv.platform.iam.infrastructure.persistence.jpa.entities.RolePersistenceEntity;
import com.bv.platform.iam.infrastructure.persistence.jpa.repositories.RolePersistenceRepository;
import com.bv.platform.iam.infrastructure.persistence.jpa.repositories.UserPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserPersistenceRepository userPersistenceRepository;
    private final RolePersistenceRepository rolePersistenceRepository;

    public UserRepositoryImpl(UserPersistenceRepository userPersistenceRepository,
                              RolePersistenceRepository rolePersistenceRepository) {
        this.userPersistenceRepository = userPersistenceRepository;
        this.rolePersistenceRepository = rolePersistenceRepository;
    }

    @Override
    public Optional<User> findById(Long id) {
        return userPersistenceRepository.findById(id).map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userPersistenceRepository.findByUsername(username).map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<User> findAll() {
        return userPersistenceRepository.findAll().stream().map(UserPersistenceAssembler::toDomainFromPersistence).toList();
    }

    @Override
    public User save(User user) {
        var entity = UserPersistenceAssembler.toPersistenceFromDomain(user);
        var managedRoles = entity.getRoles().stream()
                .map(RolePersistenceEntity::getId)
                .map(rolePersistenceRepository::findById)
                .map(role -> role.orElseThrow(
                        () -> new IllegalStateException("Role not found while saving user")))
                .collect(Collectors.toSet());
        entity.setRoles(managedRoles);
        var saved = userPersistenceRepository.save(entity);
        return UserPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userPersistenceRepository.existsByUsername(username);
    }
}
