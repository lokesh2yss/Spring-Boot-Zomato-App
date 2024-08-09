package com.codingshuttle.app.zomatoApp.dto;

import com.codingshuttle.app.zomatoApp.entities.enums.AccountStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.Role;
import com.codingshuttle.app.zomatoApp.entities.enums.VerificationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private Set<Role> roles;

    private LocalDate dob;

    private List<AddressDto> addresses;

    private AddressDto defaultAddress;

    private VerificationStatus emailVerificationStatus;

    private VerificationStatus phoneVerificationStatus;

    private AccountStatus accountStatus;

    private LocalDateTime createdAt;

}
