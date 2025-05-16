package com.elixrlabs.doctorpatientmanagementsystem.repository;

import com.elixrlabs.doctorpatientmanagementsystem.model.UsersModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<UsersModel, UUID> {
    Optional<UsersModel> findByUserName(String username);
}
