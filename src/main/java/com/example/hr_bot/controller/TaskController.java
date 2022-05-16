package com.example.hr_bot.controller;

import com.example.hr_bot.dto.ApiResponse;
import com.example.hr_bot.dto.TaskDto;
import com.example.hr_bot.entity.Task;
import com.example.hr_bot.repository.TaskRepository;
import com.example.hr_bot.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("task")
@RestController
@RequiredArgsConstructor
public class TaskController {

    final TaskService taskService;
    final TaskRepository taskRepository;

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody TaskDto dto) {
        ApiResponse response = taskService.add(dto);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    //task egasi taskni statusini o'zgartirishi mn enum va completedni
    @GetMapping("/{taskId}")
    public ResponseEntity changeStatus(@PathVariable(value = "taskId") Integer id, @RequestParam String status) {
        ApiResponse response = taskService.changeStatus(id, status);
        return ResponseEntity.status(response.isSuccess() ? 200 : 409).body(response);
    }

    //qaysidir userga tegshli tasklar ro'yxatini topib kelish
    @GetMapping("/getTaskByUser/{id}")
    public ResponseEntity getTaskByUser(@PathVariable Long id) {
        List<Task> allByTakenUser_id = taskRepository.findAllByTakenUser_Id(id);
        return ResponseEntity.ok().body(allByTakenUser_id);
    }


    //vaqtida bajarilgan tasklar ro'yxati
    @GetMapping("/getTaskByDate")
    public ResponseEntity getTaskByDate(){
        List<Task> allByCompleted = taskRepository.getAllByCompleted();
        return ResponseEntity.ok().body(allByCompleted);
    }

    //o'z vaqtida bajarilmagan tasklar ro'yxati
    @GetMapping("/getTaskByComplitedDate")
    public ResponseEntity getTaskByComplitedDate(){
        List<Task> allByCompleted = taskRepository.getAllByCompletedDate();
        return ResponseEntity.ok().body(allByCompleted);
    }

    //hozircha bajarilmagan tasklar
    @GetMapping("/getTaskByStatus")
    public ResponseEntity getTaskByStatus(){
        List<Task> allByCompletedFalse = taskRepository.findAllByCompletedFalse();
        return ResponseEntity.ok().body(allByCompletedFalse);
    }

    //fromUser tasklari o'ziga ham ko'rinishi kerak
    @GetMapping("/getTaskByFromUser/{id}")
    public ResponseEntity getTaskByFromUser(@PathVariable Long id){
        List<Task> allByGivenUser_id = taskRepository.findAllByGivenUser_Id(id);
        return ResponseEntity.ok().body(allByGivenUser_id);
    }

    //tasklar ro'yxati pageable holatda chiqishi kerak
    @GetMapping("/getTaskByPeagble")
    public ResponseEntity getTaskByPeagble(@RequestParam int size, @RequestParam int page){
        Pageable pageRequest = PageRequest.of(page, size);
        Page<Task> all = taskRepository.findAll(pageRequest);
        return ResponseEntity.ok().body(all);


    }
    //


}
