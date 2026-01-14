package com.tecsoluction.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tecsoluction.course.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
