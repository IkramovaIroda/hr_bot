package com.example.hr_bot.repository;


import com.example.hr_bot.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByName(String name);
//    @Query(value = "select * from users",
//            nativeQuery = true)
//    List<UserProjection> getAll();


}
