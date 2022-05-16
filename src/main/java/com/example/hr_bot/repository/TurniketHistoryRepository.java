package com.example.hr_bot.repository;


import com.example.hr_bot.entity.TurniketHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurniketHistoryRepository extends JpaRepository<TurniketHistory, Integer> {
}
