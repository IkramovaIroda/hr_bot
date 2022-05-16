package com.example.hr_bot.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto {
    private String name, description;
    //    private Long userFrom;
    private Long userId;
    private Timestamp dueDate;
}
