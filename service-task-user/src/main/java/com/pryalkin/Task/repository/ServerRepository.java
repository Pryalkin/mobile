package com.pryalkin.Task.repository;

import com.pryalkin.Task.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {

    Optional<Server> findByServerNameAndServerPassword(String serverName, String serverPassword);
}
