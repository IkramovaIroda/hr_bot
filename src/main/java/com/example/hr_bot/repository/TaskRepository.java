package com.example.hr_bot.repository;


import com.example.hr_bot.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByTakenUser_Id(Long takenUser_id);
    @Query(value = "select * from task where completed_time < due", nativeQuery = true)
    List<Task> getAllByCompleted();

    @Query(value = "select * from task where completed_time is not null and  completed_time > task.due", nativeQuery = true)
    List<Task> getAllByCompletedDate();

    List<Task> findAllByCompletedFalse();
    List<Task> findAllByGivenUser_Id(Long givenUser_id);

    @Override
    Page<Task> findAll(Pageable pageable);
}
