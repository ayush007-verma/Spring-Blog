package io.mountblue.blogapplication.repositories;

import io.mountblue.blogapplication.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email);

}
