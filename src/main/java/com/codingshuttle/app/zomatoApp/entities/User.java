package com.codingshuttle.app.zomatoApp.entities;

import com.codingshuttle.app.zomatoApp.entities.enums.AccountStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.Role;
import com.codingshuttle.app.zomatoApp.entities.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private LocalDate dob;

    private List<Address> addresses;

    private Address defaultAddress;

    @Enumerated(EnumType.STRING)
    private VerificationStatus emailVerificationStatus;

    @Enumerated(EnumType.STRING)
    private VerificationStatus phoneVerificationStatus;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
