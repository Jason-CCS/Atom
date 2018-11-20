package com.jason.atom.admin.repository;

import com.jason.atom.admin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmailAndAndPassword(String email, String password);

    User findUserByName(String name);

    @Override
    <S extends User> S save(S s);
}
