package com.example.hr_bot.entity;

import com.example.hr_bot.entity.enums.Usertype;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username, password, email;

    @Column(unique = true)
    private String number;

    //kim qaysi bosqichda
    @Column(unique = true, nullable = false)
    private String chatId;
    private String state;
    private Usertype usertype=Usertype.TELEGRAM_USER;

    @ManyToOne
    private Company company;

    public User(String chatId, String state) {
        this.chatId = chatId;
        this.state = state;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roleList; //user_roleList


}
