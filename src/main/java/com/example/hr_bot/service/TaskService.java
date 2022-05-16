package com.example.hr_bot.service;

import com.example.hr_bot.dto.ApiResponse;
import com.example.hr_bot.dto.TaskDto;
import com.example.hr_bot.entity.Role;
import com.example.hr_bot.entity.Task;
import com.example.hr_bot.entity.User;
import com.example.hr_bot.entity.enums.TaskStatus;
import com.example.hr_bot.repository.TaskRepository;
import com.example.hr_bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskService {
    final UserRepository userRepository;
    final TaskRepository taskRepository;

    public ApiResponse add(TaskDto dto) {
//        Set<Role> fromUserRoles = user.getRoleList();

        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        Set<Role> toUserRoles = optionalUser.get().getRoleList();

//        if (fromUserRoles.size() > toUserRoles.size()) {
            Task task = new Task();
            task.setDescription(dto.getDescription());
//            task.setGivenUser(user);
            task.setTakenUser(optionalUser.get());
            task.setDue(dto.getDueDate());
            task.setStatus(TaskStatus.NEW);
            task.setName(dto.getName());
            taskRepository.save(task);
//        }
        return ApiResponse.builder().success(true).message("Vazifa biriktirildi!").build();
    }

    public ApiResponse changeStatus(Integer id, String status) {
        Optional<Task> optionalTask = taskRepository.findById(id);
        Task task = optionalTask.get();
        TaskStatus taskStatus = TaskStatus.valueOf(status);

        if (task.getStatus().ordinal() < taskStatus.ordinal()) {
            if (taskStatus.ordinal() == 3 && task.getDue().before(new Timestamp(new Date().getTime()))) {
                task.setCompletedTime(new Timestamp(new Date().getTime()));
                task.setCompleted(true);
                task.setStatus(TaskStatus.COMPLETED);
            } else {
                task.setStatus(taskStatus);
            }
        } else {
            return ApiResponse.builder().message("Statusni bu holatga qaytarish mnmas!").success(false).build();
        }
        taskRepository.save(task);
        return ApiResponse.builder().message("Changed").success(true).build();
    }
}
