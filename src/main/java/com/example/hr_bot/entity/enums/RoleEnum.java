package com.example.hr_bot.entity.enums;

public enum RoleEnum {
    //static
    ROLE_USER("user"), //tengkuchli == RolEnum enum = new RoleEnum("Role USER")
    ROLE_ADMIN("admin"),
    ROLE_MODERATOR("moderator");
    private String description;

    RoleEnum(String description) {
        this.description = description;
    }
}
