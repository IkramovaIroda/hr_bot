package com.example.hr_bot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Month;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    private double price;

    @CreationTimestamp
    private Timestamp dueDate;

    @Enumerated(EnumType.STRING)
    private Month month; //java time Api enum

}
