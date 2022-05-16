package com.example.hr_bot.repository;

import com.example.hr_bot.entity.User;
import com.example.hr_bot.entity.enums.Usertype;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByChatId(String chatId);

    List<User> findAllByUsertype(Usertype usertype);
}
