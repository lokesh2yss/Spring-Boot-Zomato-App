package com.codingshuttle.app.zomatoApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    private UserDto user;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private Integer pinCode;

    private String landmark;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
