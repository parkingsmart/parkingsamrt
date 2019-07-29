package com.oocl.parkingsmart.repository;


import com.oocl.parkingsmart.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long> {
}
