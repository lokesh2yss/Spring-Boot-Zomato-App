package com.codingshuttle.app.zomatoApp.entities;

import com.codingshuttle.app.zomatoApp.entities.enums.AccountStatus;
import com.codingshuttle.app.zomatoApp.entities.enums.Role;
import com.codingshuttle.app.zomatoApp.entities.enums.VerificationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyGroup;

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

    private String password;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private LocalDate dob;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "default_address_id", referencedColumnName = "id")
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
