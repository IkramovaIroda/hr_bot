package com.example.hr_bot.service;

import com.example.hr_bot.dto.ApiResponse;
import com.example.hr_bot.dto.UserDTO;
import com.example.hr_bot.entity.Company;
import com.example.hr_bot.entity.User;
import com.example.hr_bot.entity.enums.Usertype;
import com.example.hr_bot.repository.CompanyRepository;
import com.example.hr_bot.repository.RoleRepository;
import com.example.hr_bot.repository.TurniketRepository;
import com.example.hr_bot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import javax.persistence.ManyToOne;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final TurniketRepository turniketRepository;
    final CompanyRepository companyRepository;

    @SneakyThrows
//    public ApiResponse add(UserDTO dto) {
//        String role = dto.getRole();
//        RoleEnum roleEnum = RoleEnum.valueOf(role);
//        //kim qoshilyapti user moderator
////        for (Role role1 : principal.getRoleList()) {
//            //director
//            if (role1.getRoleName().equals(RoleEnum.ROLE_ADMIN)) {
//                //admin ekan moderator va user qo'sha olishi kerak
//                if (roleEnum.equals(RoleEnum.ROLE_MODERATOR) || roleEnum.equals(RoleEnum.ROLE_USER)) {
//                    User user = new User();
//                    user.setEmail(dto.getEmail());
//                    user.setUsername(dto.getUsername());
//                    user.setPassword(("1111"));
//
//                    Optional<Role> byRoleName = roleRepository.findByRoleName(roleEnum);
//                    user.setRoleList(new LinkedHashSet(Collections.singleton(byRoleName.get())));
//
//                    User save = userRepository.save(user);
//
//                    Turniket turniket = new Turniket();
//                    turniket.setUser(save);
//                    turniketRepository.save(turniket);
//                }
//            } else if (role1.getRoleName().equals(RoleEnum.ROLE_MODERATOR)) {
//                if (role.equals(RoleEnum.ROLE_USER.name())) {
//                    User user = new User();
//                    user.setEmail(dto.getEmail());
//                    user.setUsername(dto.getUsername());
//                    user.setPassword(("1111"));
//
//                    Optional<Role> byRoleName = roleRepository.findByRoleName(roleEnum);
//                    user.setRoleList(new LinkedHashSet(Collections.singleton(byRoleName.get())));
//
//                    Turniket turniket = new Turniket();
//                    turniket.setUser(user);
//                    turniketRepository.save(turniket);
//                    userRepository.save(user);
//                }
//            }
////            } else {
////                throw new ResourceNotFoundException("Szda bunaqa huquq yoq");
////            }
//        }
//        return ApiResponse.builder().message("Added!").success(true).build();
//    }

    public ApiResponse edit(Integer id, UserDTO dto) {
        return null;
//        List<Role> roles = roleRepository.findAllById(dto.getRoleList());
//        if (roles.isEmpty()) return new ApiResponse("Roles not found", false);
//
//        Optional<User> optionalUser = userRepository.findById(id);
//        if (optionalUser.isEmpty()) return new ApiResponse("User not found", false);
//
//        User user = optionalUser.get();
//
//        user.setEmail(dto.getEmail());
//        user.setUsername(dto.getUsername());
//        user.setPassword(passwordEncoder.encode(dto.getPassword()));
//        user.setRoleList(new LinkedHashSet<>(roles));
//
//        userRepository.save(user);
//
//        return new ApiResponse("Edited", true);
    }

    public ApiResponse add(UserDTO dto) {
        userRepository.save(User.builder()
                        .company(companyRepository.findAll().get(0))
                        .usertype(Math.round(Math.random())==1? Usertype.EMPLOYEE:Usertype.MANAGER)
                        .username(dto.getUsername())
                .build());
        return ApiResponse.builder()
                .success(true)
                .message("User created!")
                .build();
    }
}
